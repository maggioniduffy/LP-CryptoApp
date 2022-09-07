# Crypto Prices

## TP4 Laboratorio de Programacion 2021 | Universidad Nacional del Comahue

- Faustino Maggionin Duffy

### Introduccion

- Crypto Prices es una aplicación de android nativa que muestra precios de distintas criptomonedas, más precisamente el precio actual y el precio que tenían hace 7 días. Esta información es conseguida a través nuestra API: https://stormy-citadel-21324.herokuapp.com/api/coins, también hecha como un trabajo de esta materia. A su vez esta API consulta, filtra y devuelve los datos desde Coing Gecko sumados a los de nuestro archivo guardado en el servidor el cual almacena las monedas agregadas por los usuarios.
- En la aplicación tenemos 3 pantallas, la principal muestra los datos (precios) de las monedas al dia de la fecha, la segunda los precios de hace 7 días y la tercera nos permite agregar nuestra propia moneda a la aplicación.

### Librerias

- En Crypto Prices utilizamos las siguientes librerias:
  - Retrofit, para consultar al backend.
  - Moshi, para representar los datos obtenidos con Retrofit en formato JSON.
  - Glide, para poder renderizar imagenes con facilidad en la app.
  - Firestore Cloud, como base de datos. Principalemente usada para almacenar datos localmente y
    que puedan ser accedidos sin conexion a internet.

### Arquitectura

- Usamos una arquitectura simple, una MainActivity como punto de entrada a la aplicación, la cual contiene un navegador entre los 3 Fragmentos que representan cada pantalla. Los fragments que muestran precios tienen su correspondiente Adapter que lee los datos provistos por Api Service, una clase que consulta la API utilizando Retrofit, los parsea y los muestra en pantalla según es necesario. Por cada consulta, guarda una copia de los datos en la base de datos de Firestore y cuando detecta que el dispositivo no tiene conexión a internet, en vez de llamar a ApiService, trae los datos directamente desde la copia local.

#### Estructura:

```
  └── com
    └── example
        └── offline_crypto
            ├── EndlessRecyclerViewScrollListener.kt
            ├── MainActivity.kt
            ├── models
            │   └── Coin.kt
            ├── network
            │   └── ApiService.kt
            ├── ui
            │   ├── addcoin
            │   │   ├── AddcoinFragment.kt
            │   │   └── AddcoinViewModel.kt
            │   ├── lastweek
            │   │   ├── LastWeekAdapter.kt
            │   │   ├── LastWeekFragment.kt
            │   │   └── LastWeekViewModel.kt
            │   └── today
            │       ├── HomeViewModel.kt
            │       ├── TodayAdapter.kt
            │       └── TodayFragment.kt
            └── Utils.kt
  ├── layout
│   ├── activity_main.xml
│   ├── fragment_addcoin.xml
│   ├── fragment_home.xml
│   ├── fragment_lastweek.xml
│   └── list_item.xml

```

## Ejemplos

- #### Pantalla de hoy:

  ![img.png](img.png)

- #### Pantalla de la semana pasada:
  ![img_1.png](img_1.png)
