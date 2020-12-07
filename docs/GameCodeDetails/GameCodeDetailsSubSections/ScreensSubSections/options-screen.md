---
layout: default
title: Options Screen
nav_order: 2
parent: Screens
grand_parent: Game Code Details
permalink: /GameCodeDetails/Screens/OptionsScreen
---

# Navigation Structure
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Menu Screen

The screen handles the logic and graphics related to the menu that is loaded upon the game starting up.

![option-screen.png](../../../assets/images/options-screen.png)

The class file for it is `OptionsScreen.java` which can be found in the `Screens` package.

## Functionality

The menu screen's only real job is to allow the player to select between updating volume level and the image the cat displays.
Upon selecting an option, `OptionsScreen` will change `ScreenCoordinator's` game state which will force it to update the volume/cat.

## Graphics

The background of the menu screen uses a `Map` specifically made for it (`TitleScreenMap.java` in the `Maps` package), which is the same type of `Map` class which
is used when actually playing the platformer game. While any image could have been used, I thought it'd be more fun to use a map as the background.

The little blue square moves based on the value of `currentMenuItemHovered` (which changes value when up or down is pressed) to be in front
of the currently hovered item's text. The text that is hovered over will also change color to gold while the one not hovered will change color to black.

