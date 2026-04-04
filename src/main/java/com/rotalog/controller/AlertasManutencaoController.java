package com.rotalog.controller;

import com.rotalog.domain.AlertaManutencao;
import com.rotalog.service.AlertaManutencaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/alertas-manutencao")
public class AlertasManutencaoController {

	private final AlertaManutencaoService alertaManutencaoService;

	public AlertasManutencaoController(AlertaManutencaoService alertaManutencaoService) {
		this.alertaManutencaoService = alertaManutencaoService;
	}

	@GetMapping
	public ResponseEntity<List<AlertaManutencao>> listar(@RequestParam(required = false) String status) {
		return ResponseEntity.ok(alertaManutencaoService.listarPorStatus(status));
	}

	@PostMapping("/verificar")
	public ResponseEntity<Void> verificar() {
		log.info("Verificação manual de alertas de manutenção solicitada");
		alertaManutencaoService.verificarEGerarAlertas();
		alertaManutencaoService.processarAlertas();
		return ResponseEntity.ok().build();
	}
}
