# Accounts

Demostración de comunicación entre App y API REST usando Clean Arquitecture.

Se ha dejado en un segundo plano el diseño, únicamente se ha centrado en la parte del código, mostrando Account Name, IBAN y Account Balance para cada una de las cuentas,
a través de una custom view que permite diferencenciar si el dato contiene información o por el contrario está vacio o nulo , en cuyo caso no se mostrará el label correspondiente.

Se añade un Switch personalizado para visualizar todas o sólo las cuentas visible

Se ha utilizado mockable.io para  mockear el siguiente JSON:

```
{
"accounts": [
{
"accountBalanceInCents": 985000,
"accountCurrency": "EUR",
"accountId": 748757694,
"accountName": "Hr P L G N StellingTD",
"accountNumber": 748757694,
"accountType": "PAYMENT",
"alias": "",
"isVisible": true,
"iban": "NL23INGB0748757694"
},
{
"accountBalanceInCents": 1000000,
"accountCurrency": "EUR",
"accountId": 700000027559,
"accountName": ",",
"accountNumber": 748757732,
"accountType": "PAYMENT",
"alias": "",
"isVisible": false,
"iban": "NL88INGB0748757732"
},
{
"accountBalanceInCents": 15000,
"accountCurrency": "EUR",
"accountId": 700000027559,
"accountName": "",
"accountNumber": "H 177-27066",
"accountType": "SAVING",
"alias": "G\\UfffdLSAVINGSdiacrits",
"iban": "",
"isVisible": true,
"linkedAccountId": 748757694,
"productName": "Oranje Spaarrekening",
"productType": 1000,
"savingsTargetReached": 1,
"targetAmountInCents": 2000
}
],
"failedAccountTypes" : "CREDITCARDS",

"returnCode" : "OK"
}
```



## Estructura del proyecto


Es una arquitectura clean, siguiendo un patrón MVVM de un único estado.

Gradle se estructura:

-A nivel de proyecto: Aquí se definen las bibliotecas y cada una de las versiones
-A nivel de módulo: Se utilizan alias para implementar cada una de estas versiones

Se separa el concepto de versión / implementación


El código se divide en varios packagename, donde las raices de los mismos son:

core : Aspectos relacionados con extensiones y clases abstractas propias de arquitectura

data:  Endpoints, manejadores de conectividad, y todo lo relacionado a los modelos de servidor y respuestas

domain: Lógica del dominio, aquí además de usar un patrón repository, añado cada caso de uso concreto que usará el viewmodel. 
Aquí también introduzco el inyector de dependencias, en este caso koin

presentation: Pantallas, ViewModels y adapters, junto con sus posibles estados

utils: Fichero Constants

Los estados asociados a cada una de las pantallas se representan mediante un NombreActividadScreenState, y representa el estado de la pantalla en ese momento. Se encamarca dentro de una sealed class que maneja una clase parametrizada, que será la clase concreta de esa pantalla. Puede tener un estado Loading, y un estado Render, y es en este último donde se maneja uno de los posibles estados de pantalla definidos, 
en caso de MainScreenState es una clase: GetAccountsFromServer

En el proyecto se usa jetpack para los ViewModels, y se utilizan corutinas de kotlin a partir de la versión 1.3 para la programación multihilo. Es igualmente aplicable al uso de programación reactiva. Aquí los casos de uso se ejecutan en un contexto que permite llamadas asíncronas, tanto en el consumo de API como en cualquier operación definida dentro del viewmodel. Estos casos de uso realizan la parte funcional y el consumo de APIs.

Este consumo de APIs se hace a través del patrón repository AccountRepository. Lleva un manejador de conexiones para verificar que existe conexión y se envían los datos asociados y las llamadas correspondientes definidas en ApiService



Las respuestas llevan un Either como devolución en las llamadas: Ejemplo


```
fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure,R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(transform((response.body() ?: default)))
                false -> {

                    Either.Left(Failure.ServerErrorCode(response.code()))
                }
            }
        } catch (exception: Throwable) {
            Either.Left(Failure.ServerException(exception))
        }
    }
```

Either es un monad, unión disjuntiva, devuelve siempre un valor, o bien la clase izquierda (en cuyo caso será un fallo) o bien la derecha (entonces será el dato que buscas). 
Esto es así ya que de esta forma se controla cada uno de los fallos posibles a la hora de devolver la petición; Failure.NullResult, Failure.NetworkConnection son dos ejemplos, pero la estructura permite un modelo abierto de códigos de error de petición o extender de la clase abstracta FeatureFailure para decidir qué casos de uso tienen que errores.

El caso de uso GetAccounts además incluye una clase caché simple, para evitar peticiones redundantes, que puede sobreescribirse
