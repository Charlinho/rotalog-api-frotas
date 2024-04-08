# RotaLog - API Frotas

Microsserviço de gerenciamento de frotas do sistema RotaLog.

## Tech Stack

- **Java 11** com **Spring Boot 2.7.14**
- **Spring Data JPA** com **PostgreSQL**
- **Flyway** para migrações de banco
- **Lombok** para redução de boilerplate
- **RestTemplate** para comunicação entre serviços

## Pré-requisitos

- Java 11+
- PostgreSQL rodando (via docker-compose do rotalog-workspace)
- api-notificacoes rodando em `http://localhost:5000` (opcional)
- api-entregas rodando em `http://localhost:3000` (opcional)

## Como rodar

```bash
# Subir o banco de dados
cd ../rotalog-workspace && docker-compose up -d postgres

# Rodar a aplicação
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080/api`

## Endpoints

### Veículos (`/api/veiculos`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/veiculos` | Listar todos os veículos |
| GET | `/api/veiculos/{id}` | Buscar veículo por ID |
| GET | `/api/veiculos/placa/{placa}` | Buscar veículo por placa |
| GET | `/api/veiculos/status/{status}` | Listar veículos por status |
| GET | `/api/veiculos/estatisticas` | Estatísticas da frota |
| POST | `/api/veiculos` | Registrar novo veículo |
| PUT | `/api/veiculos/{id}` | Atualizar veículo |
| PATCH | `/api/veiculos/{id}/quilometragem` | Atualizar quilometragem |
| PATCH | `/api/veiculos/{id}/desativar` | Desativar veículo |
| PATCH | `/api/veiculos/{id}/reativar` | Reativar veículo |

### Motoristas (`/api/motoristas`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/motoristas` | Listar todos os motoristas |
| GET | `/api/motoristas/{id}` | Buscar motorista por ID |
| GET | `/api/motoristas/cnh/{cnh}` | Buscar motorista por CNH |
| GET | `/api/motoristas/status/{status}` | Listar por status |
| GET | `/api/motoristas/cnh-vencida` | Listar com CNH vencida |
| POST | `/api/motoristas` | Cadastrar motorista |
| PUT | `/api/motoristas/{id}` | Atualizar motorista |
| PATCH | `/api/motoristas/{id}/desativar` | Desativar motorista |
| PATCH | `/api/motoristas/{id}/reativar` | Reativar motorista |

### Manutenções (`/api/manutencoes`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/manutencoes` | Listar todas as manutenções |
| GET | `/api/manutencoes/{id}` | Buscar manutenção por ID |
| GET | `/api/manutencoes/veiculo/{veiculoId}` | Listar por veículo |
| GET | `/api/manutencoes/pendentes` | Listar pendentes |
| GET | `/api/manutencoes/veiculo/{veiculoId}/ultima` | Última manutenção |
| POST | `/api/manutencoes` | Agendar manutenção |
| PATCH | `/api/manutencoes/{id}/iniciar` | Iniciar manutenção |
| PATCH | `/api/manutencoes/{id}/concluir` | Concluir manutenção |
| PATCH | `/api/manutencoes/{id}/cancelar` | Cancelar manutenção |

### Health Check (`/api/health`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/health` | Status do serviço e dependências |

## Comunicação entre serviços

| Destino | URL | Tipo |
|---------|-----|------|
| api-notificacoes | `http://localhost:5000` | HTTP POST (notificações) |
| api-entregas | `http://localhost:3000` | HTTP GET (consultas) |

## Dívida Técnica Conhecida

- [ ] URLs de serviços hardcoded nas classes
- [ ] RestTemplate instanciado manualmente
- [ ] Sem @ControllerAdvice para tratamento global de erros
- [ ] Sem Bean Validation nos DTOs
- [ ] Retornando entidades JPA diretamente nos endpoints
- [ ] TABLE_PER_CLASS no Veiculo (strategy inadequada)
- [ ] Sem paginação nos endpoints de listagem
- [ ] Sem testes unitários ou de integração
- [ ] Sem Spring Actuator
- [ ] Sem CORS configurado
- [ ] Sem profiles de ambiente
- [ ] Status como String em vez de Enum
- [ ] @Autowired field injection em vez de constructor injection
- [ ] Exceções de notificação engolidas no service
- [ ] Typo no método obterEstatisticasFreita()
