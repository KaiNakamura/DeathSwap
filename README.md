[![License: MIT](https://img.shields.io/badge/License-MIT-brightgreen.svg)](https://github.com/KaiNakamura/DeathSwap/blob/master/LICENSE)
[![maven](https://github.com/KaiNakamura/DeathSwap/workflows/maven/badge.svg)](https://github.com/KaiNakamura/DeathSwap/actions)
[![downloads](https://img.shields.io/github/downloads/KaiNakamura/DeathSwap/total)](https://github.com/KaiNakamura/DeathSwap/releases)

<p align="center">
	<h1 align="center">Death Swap</h1>
	<h2 align="center">
		PvP Minigame that Swaps the Locations of Players
		<br />
		<a href="https://github.com/KaiNakamura/DeathSwap/issues">Report Bug</a>
		·
		<a href="#installation"><b>INSTALLATION INSTRUCTIONS</b></a>
	</h2>
</p>

## Table of Contents

* [About](#about)
* [Installation](#installation)
* [Commands](#commands)
* [Config](#config)
* [Issues](#issues)
* [License](#license)

## About

This project is a spigot plugin that swaps the locations of players. Players try to trap each other to win.

## Installation

Download the latest version from [releases](https://github.com/KaiNakamura/DeathSwap/releases) and place the JAR file in the plugins folder of your server.

## Commands

| Command | Description | Notes |
| --- | --- | --- |
| `/start` | Start the game | Sets time to 0 and gives all players a tracking compass, full health, and full hunger |
| `/stop` | Stop the game | |
| `/config [player]` | Open config gui | If more than one player is editing the config, the latest changes take priority, see [config](#config) for more details |
| `/swap [seconds]` | Set time in seconds before next swap | If no time is given, swaps immediately |

*If a command from this plugin conflicts with a command from another plugin use the prefix:*

`/deathswap:<command>`

## Config

The config file is a list of editable settings to change different aspects of gameplay. The easiest way to change the config is with the `/config` command.

For more control over the config, navigate to the [config.yml](https://github.com/KaiNakamura/DeathSwap/blob/master/src/main/resources/config.yml) file in the plugins folder of your server under `DeathSwap\config.yml`

| Attribute | Default | Description | Notes |
| --- | --- | --- | --- |
| `swap-time` | 300 seconds | The time in seconds until players swap | |
| `warning-time` | 20 seconds | The time in seconds before swapping in which warnings will be given | |
| `warning-sounds` | true | Play sound when warning is given | |
| `world-border-size` | 300 blocks | The size in blocks of the world border | Recommended to keep within the spawn chunks for optimal performance (19 chunks or 304 blocks) |

## Issues

To report a bug or to request a feature go [here](https://github.com/KaiNakamura/DeathSwap/issues).

## License

Distributed under the [MIT License](https://github.com/KaiNakamura/DeathSwap/blob/master/LICENSE).
