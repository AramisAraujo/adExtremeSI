## Sinópse

Sistema de anúncios ad-extreme: pessoas ou empresas possam publicar vários tipos de anúncios, tanto para venda de imóveis ou móveis bem como serviços e empregos.
Os usuários pesquisam por anúncios por meio de tag/categoria ou pelo título do mesmo e ordena os mesmos por: data, preço, ou classificação do anunciante. 
Eles podem classificar o anunciante e contactar os mesmos por meio da plataforma ou por meio dos contatos disponibilizados pelos anunciantes. 
Os anunciantes podem determinar o tempo dos anúncios bem como determinar a data do início da publicação do anúncio (similar: olx, mercado livre).

## Como contribuir

As atividades a ser realizadas estão no [Trello do grupo](https://trello.com/b/MTmKxYdo/ad-extreme-si) e foram formadas a partir da [Especificação do Projeto](https://docs.google.com/document/d/10Zt_vvNYfyVgI50dHmww4ryOkxJ5b0jYjULZNjY8vLk/pub).

Cada decisão tomada deve ser documentada na [Documentação do Projeto](https://docs.google.com/document/d/1OrBMGMNYKpr1rcjQM5CY8AUnzH9d4SGjpjB1likm2M0).

Para iniciar o desenvolvimento de alguma US definida no trello, crie um 'branch' que segue a seguinte nomenclatura : 

`feature/{US_0x_nome_curto}` para desenvolvimento de alguma funcionalidade relacionada à alguma User Story.

E `fix/{funcionalidade_ajustada}` para desenvolvimento de algum ajuste que precisa ser feito.

Após a consolidação do desenvolvimento o autor deve, primeiramente, atualizar o 'branch' desenvolvido realizando um `Merge` com o branch `master` resolvendo os conflitos que podem aparecer.

Em seguida deve realizar um `Pull Request` para que as mudanças sejam debatidas pelo grupo, que deve sugerir modificações. 
Tais devem ser implementadas, para que finalmente seja aceito o `Pull Request`.


## Instalação

1. Instale o banco de dados `PostGres 9.5` (https://www.postgresql.org), criando uma conta com login `postgres` e senha `admin` (esta conta já é criada na instalação, basta definir a senha). Inicie o banco de dados na porta 5432 (configurada automaticamente).

2. Vá ao arquivo `application.properties` e retire o comentário da seguinte linha:
`spring.jpa.hibernate.ddl-auto=create`.

3. Execute o main da classe `InitialPoint`

4. Acesse a aplicação no navegador, através da URL: (http://localhost:8080/ad-extreme).

5. Comente novamente a linha indicada no arquivo `application.properties`, para que os dados não sejam recriados e perdidos na próxima reexecução.
