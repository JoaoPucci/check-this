# check-this
Simple implementation for a client of REST API for getting and posting events

Notas:
- Utilizada a dependência do Picasso para agilizar o carregamento de imagens.
- Utilizada a dependência do Retrofit para agilizar as requisições HTTP.
- Utilizada a depencência do Gson para realizar o parse do json das requisições para objetos e também
para facilitar a transição de objetos entre activities.
- Utilizada a dependência do play-service-maps para utilização da API de mapas da google.

Algumas melhorias não realizadas em fator do tempo disponível:
- Testes Unitários
- Melhoria do design da lista de eventos.
- Interface para o usuário entrar com seus dados pessoais e estes serem salvos nas preferências, para depois serem enviados
no check in. (No momento está enviando um usuário estático).
- Implementar todos os campos na tela de detalhes.
- Dividir em fragments.
- Implementar a lista e a tela de detalhes lado a lado se o tamanho de tela permitir.

Muito Obrigado!
