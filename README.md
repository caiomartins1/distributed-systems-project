# Enunciado 41375 - 41178 - 40727

Queremos uma aplicação que permita gerir os produtos de um tipo de negócio.
Dependendo do tipo de negócio, os produtos devem estar organizados em categorias;
Cada produto deve ter um preço de compra e um preço de venda;
Cada produto deve ter um stock mínimo, isto é, um valor a partir do qual, sempre que a
quantidade de produto seja inferior ao stock mínimo, deve ser dado um aviso para repor o
stock;
A entrada de um produto deve ser registada como uma compra;
A saída de um produto deve ser registada como uma venda;
Para cada compra e para cada venda deve ficar registada a respectiva data.
Deverá ser possível realizar as seguintes operações:
1– Registar um produto;
2 – Adicionar uma certa quantidade de um produto já existente;
3 – Dar saída de um produto (se um produto é vendido ou atinge o seu prazo de validade
é retirada a quantidade correspondente do stock);
4 – Eliminar um produto (caso deixe de existir no negócio);
5 – Consultar produtos existentes. Prever vários tipos de consultas;
6 – Consultar as vendas (listar todas/ consultar por ordem de valor/produto mais vendido/
...
7 – Consultar as compras feitas a um fornecedor (listar todas/ consultar por ordem de
valor/ ….
Pretende-se uma aplicação cliente/servidor em java RMI, em que no processo servidor
um ou vários objetos remotos disponibilizam as operações descritas acima. Devem existir
dois tipos de processos cliente:

- O processo Fornecedor e o processo Vendedor. O processo Fornecedor permitirá
  realizar as operações de registo, entrada e eliminação de produtos, deve também permitir
  consultar as operações de compras (do ponto de vista da empresa).
- O processo Vendedor tem acesso às operações de saída de produtos, consulta de
  produtos e vendas.

Os processos cliente devem ser executados dentro da organização pelos seus
funcionários. Os que tratam das compras aos fornecedores acederão aos processos do tipo
Fornecedor; os que tratam das vendas, acederão aos processos do tipo Vendedor
Sempre que é realizada uma venda, é verificado se a quantidade existente do produto
vendido permanece maior ou igual ao valor de seu stock mínimo. Caso fique inferior, o
processo Fornecedor deverá receber um callback, com um aviso para repor o stock do
produto em causa.

- A aplicação deverá ter persistência de dados, usando objectos do tipo File e
  ObjectStreams para armazenar os dados. A interface com o utilizador deverá ser
  em texto. O código deverá ser devidamente comentado. A aplicação deverá
  funcionar em várias máquinas, isto é, o servidor numa máquina e pelo menos um
  cliente noutra máquina.

A área de negócio deve ser escolhida do conjunto de temas que se segue (cada tema pode
ser escolhido no máximo por 3 grupos):
T01 – Material informático;
T02 – Pronto-a-vestir;
T03 – Livraria;
T04 – Artigos desportivos;
T05 – Peças de automóvel;
T06 – Produtos alimentares;
T07 – Mobiliário;

- No final enviar por e-mail um ficheiro ZIP ou RAR com o código fonte do trabalho
  usando o endereço: pprata-sd@di.ubi.pt até às 23:59 horas de 2 de Maio.
  (Nota: Este endereço só deve ser usado para enviar o trabalho)
- O “subject” do mail deverá conter o número do grupo, sendo da forma TP1-grupo99,
  onde 99 deve corresponder ao número do grupo.
  Defesa dos trabalhos, data e hora a publicar para a semana de 3 de maio;
  As defesas serão online, e todos os elementos do grupo devem poder executar a aplicação
  na sua máquina. Todos devem ter acesso a microfone e câmara
