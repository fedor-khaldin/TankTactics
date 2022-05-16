## General Info
### Team Name: TankTactics
### Team Members: Fedor Khaldin, Itay Volk, Wilson Wu
### Revision Date: 5/15/22
### Discord Server: [Join Now](https://discord.gg/tKKjS9Puav "Discord server")

# Project Info

## Program Purpose:
The purpose of this program is to create an enjoyable, but simple, multi-player turn based strategy game that are enjoyable for players with various understanding of the game.




## Target User Profile: 
Players of various ages looking for a simple and fun strategy game they can play with their friends.

## Feature List: 
 - Multiple Tank Types
 - The ability to use points acquired in the game to perform upgrades
 - The ability to use said points to perform actions like movement/healing/attacks
 - Various tile types that apply boosts to players who are on them (extra points, extra hp, etc etc)

### Class List:
 #### Main
 - Booster
 - FieldElement
 - Main
 - Tank
 - TankTactics
 
 #### Booster
 - EnergySupplier
 - Healer
 - HiddenBooster
 - Jumper
 - MaxEnergyBooster
 - MaxLifeBooster
 - MovementBooster
 - MovementRangeBooster
 - PowerBooster
 - Shooter
 - ShootingRangeBooster
 - Special Booster
 - UnknownBooster
 
 #### Tanks
 - AOE_Tank
 - Balanced Tank
 - DOT_Tank
 - HeavyTank
 - LightTank

### Team Responsibility: 
- Itay: Wrote and now Managing core TankTactics class, which controls the main gameplay loop along with all graphics. Also Wrote, and now Managing all special tanks classes. Contributed greatly to actual game design.
- Fedor: Wrote and now Managing Tank superclass which handles all of the logic for all tank types. Contributed greatly to actual game design.
- Wilson: Wrote and now Managing FieldElement class along with all on-field boosters. Contributed greatly to actual game design.

## Known Bugs/Workarounds:
- At the moment icons for the diffrent field elements do not work due to some difficulty in file handling between MacOs and windows, will be fixed in a later release
- Most SFX are not yet implemented 
## Key Learnings:
## Credit List:
[Inspiration for Basic Game Design](https://www.gdcvault.com/play/1017744/The-Prototype-that-was-Banned#:~:text=Overview%3A,each%20other%20to%20this%20day "GDC Talk Describing Initial Prototype")

[Sound Effects #1](https://mixkit.co/free-sound-effects/tanks/ "Free SFX Site")

## Setup Instructions
1. Import all neccesarry source files.
2. Run Main class.
3. Follow promts in command line to setup game (if a game file hasn't been loaded)
