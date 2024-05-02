# Sample App

---

## Dependências

* Java 17 ou superior;
* Maven;
* Docker;

## Run

> Garanta que as portas 3000, 5555 e 9090 não estejam sendo utilizadas por outras aplicações.

```shell
cd infra/
docker-compose up -d
```

```shell
cd .. # retorne a raiz do projeto 
cd app/ 
mvn clean install -DskipTests

cd target/
java -jar insurancechallenge-0.0.1-SNAPSHOT.jar
```

Estarão disponíveis as seguintes aplicações localmente.

- [Springboot app](http://localhost:5555/insurancechallenge/v1/products)
- [Prometheus](http://localhost:9090/)
- [Grafana](http://localhost:3000/d/fb176c76-970f-443b-8242-2dd0eab8834b/metricas-aplicacao?orgId=1&refresh=1m)

> OBS: usuário e senha do grafana localhost é [admin] sem os caracteres de colchetes.

### Collection

#### Requisição de criação de recurso

```json
curl --request POST \
  --url http://localhost:5555/insurancechallenge/v1/products \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: Insomnia/2023.5.5' \
  --header 'X-Trace-Id: opcional' \
  --data '{
    "nome": "Nome",
    "categoria": "CATEGORIA",
    "preco_base": "0.00"
}'
```

#### Requisição de atualização de recurso:

```json
curl --request PUT \
  --url http://localhost:5555/insurancechallenge/v1/products/{product_id} \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: Insomnia/2023.5.5' \
  --header 'X-Trace-Id: opcional' \
  --data '{
    "nome": "Nome",
    "categoria": "CATEGORIA",
    "preco_base": 0.00
}'
```

---

## Solução

### Regras de negócio

 - Só é possível criar um novo recurso caso não exista outro com o mesmo nome e categoria;
 - Só é possível atualizar um recurso caso já exista um produto com identificador previamente cadastrado **E** caso seu nome e preço sejam diferentes do recurso previamente cadastrado;
 - Os comportamentos anteriores forem escolhidos assim pois no problema não havia outro atributo que pudesse ser escolhido de modo a diferenciar os produtos e evitar duplicidade de itens no banco de dados;
 - Os campos (nome, categoria e preco_base) são todos obrigatórios;
 - Só será aceito como entrada do campo nome, caracteres alfanuméricos e espaços;
 - Só será aceito como entrada do campo categoria, enums do tipo VIDA, AUTO, VIAGEM, RESIDENCIAL ou PATRIMONIAL.
 - Só será aceito como entrada do campo preco_base, um número que seja maior que zero.

### Cálculo do preço tarifado

Para o cálculo do preço tarifado, haviam algumas soluções factíveis, nesse sentido optou-se por implementar o *design pattern* de *strategy*. Este padrão irá permitir maior flexibilidade e manutenibilidade do cálculo com base na categoria do produto. Caso seja necessário criar uma nova maneira de cálculo (isto é uma nova categoria de produto), basta criar uma nova estratégia e implementar a interface de `ChargedPriceStrategy`.

Além do *pattern*, foi utilizado o recurso de classe abstrata para a classe da fórmula geral do cálculo, assim como os cálculos dos diferentes impostos (COFINS, IOF e PIS). Ou seja, caso os cálculos dos impostos mudem, basta atualizar a classe abstrata e isso irá refletir automaticamente para as estratégias de cálculo.

### Repositório

```shell
.
├── app
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── com
│   │   │   │       └── freemanpivo
│   │   │   │           └── insurancechallenge
│   │   │   │               ├── api
│   │   │   │               │   ├── config
│   │   │   │               │   ├── controller
│   │   │   │               │   ├── dto
│   │   │   │               │   └── exception
│   │   │   │               ├── core
│   │   │   │               │   ├── commom
│   │   │   │               │   ├── domain
│   │   │   │               │   ├── exception
│   │   │   │               │   ├── port
│   │   │   │               │   └── usecase
│   │   │   │               ├── database
│   │   │   │               │   ├── config
│   │   │   │               │   ├── entity
│   │   │   │               │   ├── operation
│   │   │   │               │   └── repository
│   │   │   │               └── sre
│   │   │   └── resources
│   │   └── test
│   │       ├── java
│   │       │   └── com
│   │       │       └── freemanpivo
│   │       │           └── insurancechallenge
│   │       │               ├── api
│   │       │               │   ├── controller
│   │       │               │   ├── dto
│   │       │               │   └── exception
│   │       │               ├── core
│   │       │               │   ├── domain
│   │       │               │   └── usecase
│   │       │               └── database
│   │       │                   ├── operation
│   │       │                   └── repository
│   │       └── resources

└── infra
    ├── app
    ├── grafana
    └── prometheus

```

Foi utilizado para a organização de arquivos do repositório (não confundir com arquitetura), a estrutura apresentada acima.

A pasta `infra/` contem os arquivos, configurações e infraestrutura de docker para rodar aplicações de observabilidade como Prometheus e Grafana.

A pasta `app/` contem os códigos fonte da aplicação. A ideia de organização de arquivos aqui foi de separar em cada pacote as responsabilidades que representam uma camada (API, CORE, DATABASE). 

Dentro da pasta (camada) `core/` está o coração da aplicação, nosso domínio. Esta camada tem como responsabilidade construir modelos ricos e autocontidos. Nesta camada você irá encontrar o *pattern* para o cálculo do preço e abstrações das fórmulas de cálculo. Esta camada tem como ponto importante estar desacoplada das demais (api e database), através do uso de interfaces, dentro do pacote `port/`, entretanto, as outras camadas podem depender dos modelos do nosso domínio. 

Dentro da pasta (camada) `api/` estão classes referentes a exposição de rotas, inserção de `requestId` em cada uma das requisições, marshalling de json, transformação de Data Transfer Object em Domínio e vice-versa. Também nessa camada você irá encontrar como são tratadas as exceções da aplicação e como foi padronizado o retorno da API utilizando a RFC-7807.

Dentro da pasta (camada) `database/` estão classes referentes a conexão com banco de dados, entidades do Hibernate, queries e a transformação destas Entidades em Domínio.

Em suma a ideia foi 'mimetizar' algo parecido com uma arquitetura hexagonal, mas não podemos chamar de arquitetura porque não foi implementado um mecanismo que garanta que as camadas estão fazendo algo fora da sua responsabilidade. Dois mecanismos podem ser usados como  uso de módulos disponível desde o Java 9 e o uso do Maven Multimódulos com a definição das dependências. Um exemplo da última possível solução citada está presente neste [repositório](https://github.com/freemanpivo/java-multimodule).

### Tecnologias e dependências

O projeto tem como dependências:

- spring-boot-starter-data-jpa;
- h2;
- spring-boot-starter-web;
- spring-boot-starter-actuator;
- spring-boot-starter-aop;
- micrometer-registry-prometheus;
- spring-boot-starter-test (usando Mockito e JUnit 5);

Não foi considerado o uso de lombok e/ou hibernate validator pela simplicidade do projeto e *respeito* a Orientação a Objetos.

Foi considerado o uso de lib de mapeamento de Classes como o mapstruct, mas devido a simplicidade do projeto, não foi usado.