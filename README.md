
##Description

Prueba Tecnica Backend Java en esta prueba se utilizo como arquictetura clean arquitecture con el framework sprring boot 
base de datos relacional postgres 

##Requerimiento Obtenidos

1 Generar número de tarjeta 

2 Activar tarjeta

3 Bloquear tarjeta

4 Recargar saldo

5 Consulta de saldo

6 Transacción de compra

7 Consultar transacción

8 Anular Transacción

##Colleción Postman

Se encuentra la carpeta documentación Prueba Nexos Bank.postman_collection.json


##Como usar 

se necesita ejecutar primero el script que se encuntra escript.sql en la carpeta de documentación

##Swager url

http://localhost:9000/swagger-ui/index.html

##Comandos Docker

docker build -t prueba-nexos:bank .  

docker run -p 9000:9000 prueba-nexos:bank
