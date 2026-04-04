package com.rotalog.service;

import com.rotalog.domain.AlertaManutencao;
import com.rotalog.domain.Veiculo;
import com.rotalog.repository.AlertaManutencaoRepository;
import com.rotalog.repository.ManutencaoRepository;
import com.rotalog.repository.VeiculoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class AlertaManutencaoService {

	private static final String DESTINATARIO_GESTOR = "gestor@rotalog.com";
	private static final String STATUS_PENDENTE = "PENDENTE";
	private static final String STATUS_FALHA = "FALHA";
	private static final String MOTIVO_KM = "QUILOMETRAGEM_EXCEDIDA";
	private static final String MOTIVO_PRAZO = "PRAZO_EXCEDIDO";

	private final VeiculoRepository veiculoRepository;
	private final ManutencaoRepository manutencaoRepository;
	private final AlertaManutencaoRepository alertaRepository;
	private final VeiculoManutencaoService veiculoManutencaoService;
	private final NotificacaoClient notificacaoClient;

	public AlertaManutencaoService(
		VeiculoRepository veiculoRepository,
		ManutencaoRepository manutencaoRepository,
		AlertaManutencaoRepository alertaRepository,
		VeiculoManutencaoService veiculoManutencaoService,
		NotificacaoClient notificacaoClient
	) {
		this.veiculoRepository = veiculoRepository;
		this.manutencaoRepository = manutencaoRepository;
		this.alertaRepository = alertaRepository;
		this.veiculoManutencaoService = veiculoManutencaoService;
		this.notificacaoClient = notificacaoClient;
	}

	@Scheduled(cron = "${veiculo.manutencao.alerta.cron:0 0 6 * * *}")
	public void executarVerificacaoDiaria() {
		log.info("Iniciando verificação diária de alertas de manutenção preventiva");
		verificarEGerarAlertas();
		processarAlertas();
	}

	public void verificarEGerarAlertas() {
		List<Veiculo> veiculosAtivos = veiculoRepository.findByStatus("ATIVO");
		log.info("Verificando {} veículos ativos para alertas de manutenção", veiculosAtivos.size());

		for (Veiculo veiculo : veiculosAtivos) {
			if (alertaRepository.existsByVeiculoIdAndStatusNotificacao(veiculo.getId(), STATUS_PENDENTE)) {
				continue;
			}

			String motivo = resolverMotivo(veiculo);
			if (motivo != null) {
				AlertaManutencao alerta = new AlertaManutencao();
				alerta.setVeiculoId(veiculo.getId());
				alerta.setPlaca(veiculo.getPlaca());
				alerta.setModelo(veiculo.getModelo());
				alerta.setQuilometragemAtual(veiculo.getQuilometragem());
				alerta.setMotivoAlerta(motivo);
				alerta.setStatusNotificacao(STATUS_PENDENTE);
				alerta.setDataCriacao(LocalDateTime.now());
				alerta.setDataAtualizacao(LocalDateTime.now());
				alertaRepository.save(alerta);
				log.info("Alerta gerado: veiculo={}, motivo={}", veiculo.getPlaca(), motivo);
			}
		}
	}

	public void processarAlertas() {
		List<AlertaManutencao> pendentes = alertaRepository.findByStatusNotificacao(STATUS_PENDENTE);
		log.info("Processando {} alertas pendentes", pendentes.size());

		for (AlertaManutencao alerta : pendentes) {
			String mensagem = montarMensagem(alerta);
			String resultado = notificacaoClient.enviarNotificacao(
				"ALERTA_MANUTENCAO_PREVENTIVA",
				DESTINATARIO_GESTOR,
				mensagem
			);

			alerta.setStatusNotificacao(resultado);
			alerta.setDataAtualizacao(LocalDateTime.now());
			if (STATUS_FALHA.equals(resultado)) {
				alerta.setErroMensagem("Falha ao contatar rotalog-api-notificacoes");
			}
			alertaRepository.save(alerta);
			log.info("Alerta processado: veiculo={}, status={}", alerta.getPlaca(), resultado);
		}
	}

	public List<AlertaManutencao> listarPorStatus(String status) {
		if (status == null || status.isBlank()) {
			return alertaRepository.findAll();
		}
		return alertaRepository.findByStatusNotificacao(status.toUpperCase());
	}

	private String resolverMotivo(Veiculo veiculo) {
		if (veiculoManutencaoService.quilometragemExcedida(veiculo)) {
			return MOTIVO_KM;
		}
		LocalDateTime dataUltima = manutencaoRepository.findDataUltimaManutencaoConcluida(veiculo.getId());
		if (veiculoManutencaoService.prazoExcedido(dataUltima)) {
			return MOTIVO_PRAZO;
		}
		return null;
	}

	private String montarMensagem(AlertaManutencao alerta) {
		if (MOTIVO_KM.equals(alerta.getMotivoAlerta())) {
			return String.format(
				"Veículo %s (%s) atingiu %d km. Limite: %d km. Agendar manutenção preventiva.",
				alerta.getPlaca(),
				alerta.getModelo(),
				alerta.getQuilometragemAtual(),
				veiculoManutencaoService.getLimiteQuilometragem()
			);
		}
		return String.format(
			"Veículo %s (%s) com %d km não realiza manutenção há mais de %d meses. Agendar revisão.",
			alerta.getPlaca(),
			alerta.getModelo(),
			alerta.getQuilometragemAtual(),
			veiculoManutencaoService.getLimiteMeses()
		);
	}
}
