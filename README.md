# IPCheckur  

&emsp; &emsp;IPCheckur is a powerful and user-friendly Minecraft server plugin designed to help server operators easily identify players' IP addresses and their associated GeoIP locations. Utilizing **ipapi.com's database**, this plugin enables admins to monitor and manage their server's player base more effectively. With IPCheckur, server operators can gain valuable insights into their players' geographic distribution, enhancing server security and facilitating better decision-making for region-specific events and configurations.  


## Table of Contents

1. [Acknowledgements](#acknowledgements)
2. [Features](#features)
3. [Usage](#usage)

## Acknowledgements

- [MIT License](https://github.com/AddictedCoins/IPCheckur/blob/main/LICENSE)
- [IP API](https://ipapi.com/documentation)

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
/geoip USERNAME
```

