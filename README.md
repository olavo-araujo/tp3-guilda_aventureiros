# Sistema de Gestão de Aventureiros

Este projeto acadêmico é uma aplicação spring boot desenvolvida para gerenciar aventureiros e suas missões, integrando um novo domínio a um banco de dados legado (schema de auditoria e organizações).

## Como Executar o Projeto

### 1. Subir o Banco de Dados (Docker)
A aplicação depende de um banco de dados PostgreSQL legado pré-configurado. A imagem oficial utilizada está disponível em: https://hub.docker.com/r/leogloriainfnet/postgres-tp2-spring.

Para baixar e iniciar o banco de dados, certifique-se de ter o Docker instalado e execute o seguinte comando no seu terminal:

docker run --name tp3 -p 5432:5432 -d leogloriainfnet/postgres-tp3-spring:2.0-win

### 2. Rodar a Aplicação

Com o container do banco de dados , você pode iniciar a aplicação diretamente pela sua IDE executando a classe principal "Tp2SpringbootApplication.java".

### 3. Executar os Testes Automatizados

O projeto conta com arquivos de testes que validam 100% dos requisitos exigidos (Mapeamento do Legado, Regras de Negócio de Aventura e Relatórios Gerenciais).

Para rodar os testes e verificar a integridade do sistema, execute as classes dentro do pacote de testes (src/test/java/...) ou rode o comando abaixo na raiz do projeto caso esteja usando o terminal:

./mvnw test

## Operações Táticas e Marketplace da Guilda

Este projeto acadêmico também implementa funcionalidades para monitoramento de operações táticas e busca avançada de itens no marketplace da guilda, utilizando Spring Data JPA, Redis e Elasticsearch.

## Estratégia de Desempenho e Cache

Para otimizar consultas relacionadas ao painel tático e reduzir a carga no banco de dados relacional, foi implementada uma camada de cache utilizando Redis.

A anotação @Cacheable(value = "rankingTatico") foi aplicada na camada de Service, permitindo que requisições repetidas ao endpoint de ranking sejam atendidas diretamente da memória, evitando o reprocessamento de consultas complexas no banco de dados.

Para manter a consistência dos dados, foi configurado um mecanismo de expiração automática utilizando @CacheEvict em conjunto com o agendador @Scheduled(fixedRate = 60000), que limpa o cache a cada 60 segundos. Dessa forma, garante-se que novas requisições obtenham dados atualizados periodicamente. A entidade utilizada no cache implementa a interface Serializable para permitir a correta serialização dos dados.

## Nota de Infraestrutura e Banco de Dados

Durante a fase de testes e integração com o banco de dados fornecido na imagem Docker, foi identificada uma inconsistência na estrutura relacionada ao painel tático.

A estrutura mv_painel_tatico_missao, esperada como uma materialized view conforme o enunciado, estava instanciada como uma tabela vazia.

Após análise das relações do banco de dados, verificou-se que os dados consolidados estavam disponíveis na view vw_painel_tatico_missao.

Dessa forma, o mapeamento da entidade JPA foi direcionado para a view correta, garantindo o funcionamento do endpoint conforme os requisitos, incluindo regras de ordenação e filtro de período.
