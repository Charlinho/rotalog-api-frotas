package com.rotalog.service;

import com.rotalog.domain.Veiculo;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * VeiculoService - Legacy service with mixed responsibilities
 * This service contains business logic, validation, and presentation concerns
 * Intentional technical debt for the course
 * 
 * TODO: Break this into smaller services
 * TODO: Extract validation logic
 * TODO: Move notification logic to separate service
 * TODO: Add proper error handling
 */
@Service
public class VeiculoService {

    // TODO: Inject repository
    // TODO: Add logging
    // TODO: Add metrics

    /**
     * Registra um novo veículo no sistema
     * 
     * FIXME: This method is too long and does too many things
     * FIXME: Validation logic is mixed with business logic
     * FIXME: No proper error handling
     */
    public Veiculo registrarVeiculo(String placa, String modelo, Integer anoFabricacao) {
        // Validação misturada com lógica de negócio
        if (placa == null || placa.isEmpty()) {
            throw new RuntimeException("Placa é obrigatória"); // FIXME: Use proper exception
        }

        if (placa.length() != 7) {
            throw new RuntimeException("Placa deve ter 7 caracteres"); // FIXME: Use proper exception
        }

        // Validação do modelo
        if (modelo == null || modelo.isEmpty()) {
            throw new RuntimeException("Modelo é obrigatório"); // FIXME: Use proper exception
        }

        // Validação do ano
        if (anoFabricacao == null || anoFabricacao < 1900 || anoFabricacao > 2100) {
            throw new RuntimeException("Ano de fabricação inválido"); // FIXME: Use proper exception
        }

        // Criar veículo
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(placa);
        veiculo.setModelo(modelo);
        veiculo.setAnoFabricacao(anoFabricacao);
        veiculo.setStatus("ATIVO");
        veiculo.setQuilometragem(0L);
        veiculo.setDataCadastro(LocalDateTime.now());
        veiculo.setDataAtualizacao(LocalDateTime.now());

        // TODO: Save to database
        // TODO: Send notification
        // TODO: Log event
        // TODO: Update cache

        return veiculo;
    }

    /**
     * Atualiza quilometragem do veículo
     * 
     * FIXME: Mixing concerns - this should be in a separate service
     * FIXME: No validation of negative values
     * FIXME: No audit trail
     */
    public void atualizarQuilometragem(Long veiculoId, Long novaQuilometragem) {
        // TODO: Fetch from database
        // TODO: Validate
        // TODO: Update
        // TODO: Notify
        // TODO: Log

        if (novaQuilometragem < 0) {
            // FIXME: This should never happen but we're not validating
            System.out.println("AVISO: Quilometragem negativa detectada!"); // FIXME: Use logger
        }

        // TODO: Implement
    }

    /**
     * Obtém veículos por status
     * 
     * FIXME: Hardcoded status values
     * FIXME: No pagination
     * FIXME: No filtering options
     */
    public List<Veiculo> obterVeiculosPorStatus(String status) {
        // FIXME: Hardcoded status values
        if (!status.equals("ATIVO") && !status.equals("INATIVO") && !status.equals("MANUTENCAO")) {
            throw new RuntimeException("Status inválido"); // FIXME: Use proper exception
        }

        // TODO: Query database
        // TODO: Apply filters
        // TODO: Return results

        return null; // FIXME: Not implemented
    }

    /**
     * Agenda manutenção preventiva
     * 
     * FIXME: Complex business logic mixed with infrastructure concerns
     * FIXME: Hardcoded maintenance intervals
     * FIXME: No transaction management
     */
    public void agendarManutencaoPreventiva(Long veiculoId, Long quilometragemLimite) {
        // TODO: Fetch vehicle
        // TODO: Check current mileage
        // TODO: Calculate next maintenance date
        // TODO: Create maintenance record
        // TODO: Send notification to manager
        // TODO: Update vehicle status
        // TODO: Log event
        // TODO: Update cache

        // FIXME: Hardcoded intervals
        Long intervaloQuilometragem = 10000L;
        Integer intervaloMeses = 3;

        // FIXME: No proper date calculation
        // FIXME: No timezone handling
        // FIXME: No daylight saving time handling

        System.out.println("Manutenção agendada para " + quilometragemLimite + " km"); // FIXME: Use logger
    }

    /**
     * Calcula custo de manutenção
     * 
     * FIXME: Business logic hardcoded
     * FIXME: No configuration management
     * FIXME: No currency handling
     */
    public Double calcularCustoManutencao(String modelo, Long quilometragem) {
        // FIXME: Hardcoded costs
        Double custoPorKm = 0.05;
        Double custoBase = 500.0;

        // FIXME: No model-specific pricing
        // FIXME: No volume discounts
        // FIXME: No seasonal adjustments

        return custoBase + (quilometragem * custoPorKm);
    }

    /**
     * Registra evento de veículo
     * 
     * FIXME: No proper event sourcing
     * FIXME: No audit trail
     * FIXME: No event versioning
     */
    public void registrarEvento(Long veiculoId, String tipoEvento, String descricao) {
        // TODO: Create event record
        // TODO: Store in database
        // TODO: Publish event
        // TODO: Update cache
        // TODO: Log event

        // FIXME: Mixing concerns
        System.out.println("Evento registrado: " + tipoEvento); // FIXME: Use logger
    }

    /**
     * Obtém histórico de manutenção
     * 
     * FIXME: No pagination
     * FIXME: No filtering
     * FIXME: No sorting
     */
    public List<String> obterHistoricoManutencao(Long veiculoId) {
        // TODO: Query database
        // TODO: Apply filters
        // TODO: Sort results
        // TODO: Paginate results
        // TODO: Cache results

        return null; // FIXME: Not implemented
    }

    /**
     * Verifica se veículo precisa de manutenção
     * 
     * FIXME: Complex business logic
     * FIXME: Hardcoded thresholds
     * FIXME: No proper date handling
     */
    public Boolean precisaDeManutencao(Long veiculoId) {
        // TODO: Fetch vehicle
        // TODO: Check mileage against threshold
        // TODO: Check last maintenance date
        // TODO: Check maintenance alerts
        // TODO: Return result

        // FIXME: Hardcoded threshold
        Long limiteQuilometragem = 50000L;

        // FIXME: No proper date calculation
        // FIXME: No timezone handling

        return false; // FIXME: Not implemented
    }

    /**
     * Desativa veículo
     * 
     * FIXME: No soft delete
     * FIXME: No audit trail
     * FIXME: No cascade delete handling
     */
    public void desativarVeiculo(Long veiculoId) {
        // TODO: Fetch vehicle
        // TODO: Check if vehicle has active deliveries
        // TODO: Check if vehicle has pending maintenance
        // TODO: Update vehicle status
        // TODO: Notify relevant parties
        // TODO: Log event
        // TODO: Update cache

        System.out.println("Veículo desativado: " + veiculoId); // FIXME: Use logger
    }

    /**
     * Reativa veículo
     * 
     * FIXME: No validation of reactivation conditions
     * FIXME: No audit trail
     */
    public void reativarVeiculo(Long veiculoId) {
        // TODO: Fetch vehicle
        // TODO: Check if vehicle is in good condition
        // TODO: Check if vehicle passed inspection
        // TODO: Update vehicle status
        // TODO: Notify relevant parties
        // TODO: Log event
        // TODO: Update cache

        System.out.println("Veículo reativado: " + veiculoId); // FIXME: Use logger
    }

    /**
     * Obtém estatísticas de frota
     * 
     * FIXME: Complex query logic
     * FIXME: No caching
     * FIXME: No pagination
     */
    public String obterEstatisticasFreita() {
        // TODO: Query database for fleet statistics
        // TODO: Calculate averages
        // TODO: Calculate totals
        // TODO: Format response
        // TODO: Cache results

        // FIXME: Typo in method name (Freita instead of Frota)
        // FIXME: No proper response object
        // FIXME: No error handling

        return "{}"; // FIXME: Not implemented
    }

    /**
     * Sincroniza dados com sistema externo
     * 
     * FIXME: No retry logic
     * FIXME: No circuit breaker
     * FIXME: No timeout handling
     */
    public void sincronizarComSistemaExterno() {
        // TODO: Connect to external system
        // TODO: Fetch data
        // TODO: Transform data
        // TODO: Update local database
        // TODO: Handle conflicts
        // TODO: Log events
        // TODO: Update cache

        // FIXME: No error handling
        // FIXME: No retry logic
        // FIXME: No timeout

        System.out.println("Sincronização iniciada"); // FIXME: Use logger
    }

    // TODO: Add more methods for vehicle management
    // TODO: Add proper exception handling
    // TODO: Add logging
    // TODO: Add metrics
    // TODO: Add caching
    // TODO: Add validation
    // TODO: Add authorization checks
    // TODO: Add audit trail
}
