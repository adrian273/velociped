# Changelog

## [0.9.92] 16/11/2019
## Added 
- Agregado cambio de estado en my order y all ordes
## Removed
- En header se dejo solo el icono el carrito de compra con su cantidad total

## [0.9.91] 11/11/2019
## Removed
- Removido Direccion en Edicion de usuario;
- Removido Direccion en Agregar nuevo usuario;
## Added
- Edicion de usuario
- Mensaje de bienvenida al iniciar Session;;
- Mensaje de Cerrar Session 
- Mensaje al registrar nuevo usuario
- Agregado UI de eleccion de tipo imagen en creacion y edicion

## Updated
- En prod-category se muestra la imagen por defecto o la agregada por url..
- En agregar producto se puede agregar una url con la imagen del producto;;
- Cambio en diseño de agregar productos
- En crud productos, cambio de orden de mostrar informacion
- En crud users, cambio de orden de mostrar informacion

## [0.9.9] 05/11/2019
## Updated
- Agregar productos desde el inicio de la pagina.
- Cambio en el orden que se muestran la lista de "ordenes".
## fixed
- Cambio de titulo en lista de usuarios.
## Added
- Modal con informacion del usuario en administrador.
- Edicion de tipo en ver usuarios en admin, falta QUERY y una logica para que se actualize al cerrrar modal;;
## 09/11/2019
- agregado logica para edicion de tipo de perfil en usuario


## [0.9.8] 04/11/2019
## Updated
- Estilo del Scroll en las tablas y App global
- Cambio a Dark mode en registro de usuario
- Cambio de estructura en modal de Agregar informacion de pago, ahora esta en un template aparte
## Added
- Agregado crud de usuarios (view)
- Modal de informacion de ordenes
## Fixed
- Arreglado la palabra constitucion en eleccion de comunas

## [0.9.7] 03/11/2019
## Fixed
- al editar la cantidad , si sobre pasa el stock, el valor sera el del stock del producto
## Added
- vista del crud de ventas
## Updated
- vista del crud de producos en la tabla

## [0.9.6] 30/10/2019
## Added
- Agregado las regiones y comunas en el formulario de despacho y tipo de pago (en carrito)

## [0.9.5] 29/10/2019
## Added
- Modal de confirmacion de envios y pagos en el carrito de compras
- Edicion de valores en ordenes al confirmar el la compra
- Modelo de direcciones
## Updated
- creacion de nuevas ordenes al confirmar pagos;;
- session en al crear la nueva ordern;

## [0.9.0] 28/10/2019
## Updated 
- nuevas tablas agregadas
- agregado tipo de  pago en orders al iniciar session

## [0.8.0] 27/10/2019
## Fixed
- al eliminar el producto desde el administrador, avisa mediante alerta que no se puede eliminar 
ya que esta asociado a una compra.
## Update
- icono en eliminacion de producto

 
## [0.7.0] 26/10/2019
### Add
- Interfaz de App
- Login
- Panel de Adm (crud productos)
- Carrito de compras (UI, logic)
- Vista de datos de usuario (Edicion)
- Arreglos en interfaz
- footer
- menu y cuenta
- nuevos mensajes de alerta en el carrito
### Fixed
- Cantidad total de productos agregados en el carrito de compras, al eliminar el producto del carrito.