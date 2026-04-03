package com.rotalog.service;

import com.rotalog.domain.Veiculo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VeiculoManutencaoService {

	private final long limiteQuilometragem;
	private final double custoPorKm;
	private final double custoBase;

	public VeiculoManutencaoService(
		@Value("${veiculo.manutencao.limite-quilometragem:50000}") long limiteQuilometragem,
		@Value("${veiculo.manutencao.custo-por-km:0.05}") double custoPorKm,
		@Value("${veiculo.manutencao.custo-base:500.0}") double custoBase
	) {
		this.limiteQuilometragem = limiteQuilometragem;
		this.custoPorKm = custoPorKm;
		this.custoBase = custoBase;
	}

	public boolean precisaDeManutencao(Veiculo veiculo) {
		return veiculo.getQuilometragem() != null && veiculo.getQuilometragem() >= limiteQuilometragem;
	}

	public void agendarManutencaoPreventiva(Veiculo veiculo, Long quilometragemLimite) {
	}

	public Double calcularCustoManutencao(Long quilometragem) {
		return custoBase + (quilometragem * custoPorKm);
	}
}
