# IPCheckur

A Minecraft IP detection tool that allows server operators to check players' IP addresses and their corresponding GeoIP locations using MaxMind's GeoIP Database.

## Table of Contents

1. [Acknowledgements](#acknowledgements)
2. [Features](#features)
3. [Usage](#usage)
4. [Authors](#authors)

## Acknowledgements

- [MIT License](https://github.com/AddictedCoins/IPCheckur/blob/main/LICENSE)
- [MaxMind GeoIP](https://www.maxmind.com/en/geoip2-services-and-databases)

## Features

- Check the IP address of a specific player
- Retrieve the GeoIP location of a player's IP address
- Automatically download and use MaxMind's GeoIP Database on the first server boot (longer load time expected)


## Usage

To simply check a user's IP address:

```bash
  /ip USERNAME
```

To check a user's IP address and their GeoIP location:

```bash
 /ip geoip USERNAME
```


## Authors

- [@AddictedCoins](https://github.com/AddictedCoins)

- [@Jianjianai](https://github.com/jianjianai)

