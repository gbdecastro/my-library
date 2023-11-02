# Biblioteca Virtual - Documentação

Esta é uma aplicação de exemplo que simula uma biblioteca virtual, permitindo a gestão de livros, autores e assuntos. Os livros podem estar associados a vários autores e assuntos.

## Pré-requisitos

Antes de executar a aplicação, certifique-se de ter as seguintes ferramentas instaladas:

- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose V2](https://docs.docker.com/compose/install/linux/)

## Inicializando a Aplicação

1. Clone este repositório:

```
git clone https://github.com/gbdecastro/my-library.git
```

2. Navegue até o diretório do projeto:

```
cd my-library
```

3. Execute o Docker Compose para iniciar a aplicação:

```
docker compose up
```

Isso iniciará três serviços: MySQL, Spring Boot e Angular, tornando a aplicação totalmente funcional.

4. Acesse a aplicação no seu navegador:

- [Angular App](http://localhost:4200)
- [Spring Boot (Swagger)](http://localhost:8080/swagger-ui.html)

## Utilização

- Use a interface Angular para gerenciar livros, autores e assuntos.
- A API Spring Boot oferece endpoints RESTful para realizar operações CRUD nos dados.

## Configuração

- As configurações do aplicativo estão localizadas nos arquivos de configuração do Spring Boot e Angular.

## Licença

Este projeto é licenciado sob a Licença MIT - consulte o arquivo [LICENSE.md](LICENSE.md) para obter detalhes.

## Autores

- [Gabriel de Castro](https://github.com/gbdecastro)
