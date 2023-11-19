# Projeto de Gerenciamento de Vacinações
Bem-vindo ao Projeto de Gerenciamento de Vacinações.
Este sistema é responsável por gerenciar informações de Vacinações.

##  Conteúdo do README

- [Visão Geral](#visão-geral)
- [Requisitos](#requisitos)
- [Configuração do Ambiente ](#configuração)
- [Contribuição](#contribuição)
- [Autores](#autores)
- [Referências](#referências)

  
##    Visão Geral

A api de Gerenciamento de Vacinações  permite o cadastro, atualização, remoção e consulta de informações de vacinações realizadas pelo nossos prifissionais de saúde.
## As tecnologias principais utilizadas são:
- [Spring Boot](https://spring.io/projects/spring-boot/)
- [MongoDB](https://www.mongodb.com/pt-br)
- Feign Client.
- [Postman](https://www.postman.com/)
- 
A API é projetada para fornecer as seguintes funcionalidades:

- Registro de vacinações de pacientes.
- Gerenciamento de informações sobre vacinas e pacientes.
- Estatísticas sobre vacinação, como doses aplicadas, doses atrasadas e vacinas por fabricante.

##  Requisitos
- [Java (versão 17)](https://www.java.com/)
- [MongoDB (versão 1.40.4)](https://www.mongodb.com/try/download/compass)
- [Postman ](https://www.postman.com/downloads/)


## Configuração do Ambiente

### Instalaçâo

- a. Clone este [repositório](https://github.com/daylane/registroVacinacao.git) para o seu computador
- b. Abra o projeto no Intellij
- c. 	Configure as propriedades do aplicativo:
      •	Abra o arquivo application.properties no diretório src/main/resources.
      •	Ajuste as configurações do MongoDB conforme necessário.
  
### Banco de dados

- a.  Iniciar uma nova conexão com a URL: mongodb://localhost:27017.
- b.  Criar uma nova database com o nome de "sus-api".
- c.  Dar o nome da primeira "collection" de "vacina".


###  Uso
-A API oferece vários endpoints para criar, ler, atualizar e excluir registros de vacinação, bem como para consultar informações estatísticas. Certifique-se de seguir a documentação dos endpoints.

- POST /api/vacinacoes/vacinasmock: Faz injestão de dados no banco de dados local
- GET /api/vacinacoes : Retorna uma lista de todas as vacinas cadastradas no banco de dados
- GET /api/vacinacoes/{id} : Retorna a vacina com o id especificado
- POST /api/vacinacoes/registrarvacina : Cria uma nova vacina com os dados enviados no corpo da requisição
- PUT /api/vacinacoes/atualizarVacina/{id} : Atualiza a vacina com o id especificado com os dados enviados no corpo da requisição
- DELETE /api/vacinacoes/{id} : Deleta a vacina com o id especificado

  
O formato dos dados das vacinações é o seguinte:

- Endpoint para registrar uma vacinação por ID:

      Post  http: //localhost:8082/api/vacinacao/registrar-vacinacao
  
- Post Exemplo:
  {
      "dataVacinacao": "2023-04-20",
      "cpfPaciente": "01548744878",
      "nomeVacina": "influenza trivalente",
      "dose": 1,
      "profissionalSaude": [
        {
            "cpf": "02547899965",
            "nome": "pablo escobar"
        }]
    }
- Endpoint para deletar uma vacinação por ID:

      Delete http: //localhost:8082/api/deletar-vacinacao/{id}

- Delete Exemplo de resposta:

      Status: 204 No Content


- Endpoint para atualizar vacina por ID:

      PUT http: //localhost:8082/api/vacinacao/atualizar-vacinacao/{id}"
         
- PUT exemplo de resposta:

   {
      "dataVacinacao": "2023-04-20",
      "cpfPaciente": "01548744878",
      "nomeVacina": "influenza trivalente",
      "dose": 1,
      "profissionalSaude": [
        {
            "cpf": "02547899965",
            "nome": "pablo escobar"
        }]
    }

###  Endpoints

- [/api/vacinas](https://github.com/daylane/Vacina): Gerenciamento de informações sobre vacinas.
- [/api/pacientes](#https://github.com/Lipe15/APi2): Gerenciamento de informações sobre pacientes.
- [/api/vacinacoes](#vacinacoes): Registro de vacinações de pacientes.


##  Contribuição

Se desejar contribuir para o desenvolvimento deste projeto, siga estas etapas:

1. Crie um fork do repositório.
2. Crie uma branch com sua feature: `git checkout -b minha-feature`
3. Faça commit das alterações: `git commit -m 'Adicionando nova feature'`
4. Faça push para a branch: `git push origin minha-feature`
5. Envie um Pull Request.

##  Autores

- [Felipe Santos](https://github.com/Lipe15)
- [Daylane Silva](https://github.com/daylane)
- [Gilson](https://github.com/gilsongmptj)
- [Matheus Moura](https://github.com/mtcurly)
- [Felipe Olivera](https://github.com/fel1pee)
- [Marcus Vinicius](https://github.com/MarcusViniciusBtt)


##  Referências

- https://www.java.com/pt-BR
- https://www.mongodb.com/products/tools/compass
- https://spring.io/projects/spring-boot
- https://www.postman.com/
