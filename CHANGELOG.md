# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).


## [Unreleased]

### Changed
* Simplify recipes for:
  * Band of Elvenskin
  * Belt of Strength
  * Robe of the Magi
  * Ring of Health
* Boost Hand of Midas drop chances to:
  * Coal - 40.70%
  * Iron - 29.78%
  * Gold - 13.56%
  * Diamond - 15.79%
  * Netherite - 0.14%
* Using Hand of Midas on an entity makes it drop double experience points.

## [0.1.0] - 2026-04-22

### Added
* Evasion component.
* Stats component.
* Radiant e Dire ores generated in the overworld and the nether respectively.
* Mithril Ingot item.
* Mithril Hammer item.
* Blight Stone item.
* Gloves of Haste item.
* Blades of Attack sword item.
* Ogre Axe axe item.
* Blade of Alacrity sword item.
* Staff of Wizardry sword item.
* Claymore sword item.
* Broadsword sword item.
* Talisman of Evasion item.
* Eaglesong item.
* Sacred Relic item.
* Demon Edge sword item.
* Javelin spear item.
* Monkey King Bar spear item.
* Crystalys sword item.
* Daedalus pickaxe item.
* Butterfly sword item.
* Status bar above the vanilla hotbar.
* Strength, agility, and intelligence player stats.
* Mana stat.
* Pseudo-random occurences.
* Evasion mechanic.


## [0.2.0] - 2026-05-12

### Added

* Player random attachment.
* Centralized particle animation management.
* Mana regeneration component.
* Mana cost reduction component.
* Move speed component.
* Health regeneration component.
* Health regeneration amplification component.
* Chargable component.
* Armor reduction effect.
* Bash effect.
* Disarm effect.
* Break effect.
* Tango item.
* Enchanted Mango food item.
* Dust of Appearance item.
* Gem of True Sight item.
* Void Stone item.
* Ring of Health item.
* Reaver axe item.
* Magic Stick item.
* Magic Wand item.
* Hand of Midas item.
* Shadow Amulet item.
* Blink Dagger sword item.
* Belt of Strength item.
* Band of Elvenskin item.
* Robe of the Magi item.
* Diffusal Blade sword item.
* Desolator hoe item.
* Skull Basher sword item.
* Abyssal Blade sword item.
* Shadow Blade sword item.
* Silver Edge sword item.
* Divine Rapier sword item.
* Radiance sword item.
* Sange sword item.
* Yasha sword item.
* Kaya sword item.
* Echo sabre sword item.
* Heaven's Halberd sword item.
* Swift Blink sword item.
* Arcane Blink sword item.
* Overwhelming Blink sword item.
* YetAnotherConfigLib dependency.
* ModMenu dependency.

### Changed
* Move combat logic from `Player` mixin to `CombatManager` object.
* Introduce `DotcItem*` family classes
* Build tooltips via `TooltipBuilder`


## [0.2.1] - 2026-05-14

## Added
* Mod icon.
* Russian translations.
* Ukranian translations.
* Spanish translations.
* Chinese translations.
* 10% evasion chance to Radiance item.
* `CHANGELOG.md` file
* Disarm and Stun now affect Iron Golems.

### Fixed
* Now `BlinkDagger#ensureOnGround` returns teleport position when no conditions are met.
* Strength icon no longer overlaps with chat (#38).
* Now Radiance and Overwhelming Blink don't damage peaceful mobs.
* Butterfly recipe which didn't exist back to this update for some reason.
* Whispering sound is no longer played for non `/w`, `/msg`, and `/tell` commands.

### Changed
* Update `README.md` file
* Remove `Butterfly` class and define Butterfly item as a `DotcSwordItem` item.
* Remove `Sange` class and define Sange item as a `DotcSwordItem` item.
* Remove `Yasha` class and define Yasha item as a `DotcSwordItem` item.
* Remove `Kaya` class and define Kaya item as a `DotcSwordItem` item.
* Rearrange packages in `client`.

### Removed
* 3D models for Butterfly, Daedalus and Crystalys


## [0.3.0] - 2026-05-20

### Added
* Ring of Tarrasque item.
* Heart of Tarrasque item.
* Energy Booster item.
* Vitality Booster item.
* Aeon Disk item.
* Silence effect.
* Soul Rend effect.
* Sage's Mask item.
* Oblivion Staff sword item.
* Orchid Malevolence sword item.
* Bloodthorn sword item.
* Root effect.
* Rod of Atos sword item.
* Soul Release effect.
* Urn of Shadows item.
* Spirit Soul Release effect.
* Spirit Vessel item.
* Force Staff sword item.
* Dragon Lance sword item.
* Hurricane Pike sword item.
* Kill streak announcement which can be turned on and off in the mod configuration.

### Fixed
* Radiance and Overwhelming Blink damage now is bound to player, meaning that mob drops will be given as if the player killed the mobs by hand.
* Radiant Crystal Block and Dire Crystal Block can now be reverted back to crystals.

### Changed
* No longer display mana cost for items with mana cost set to 0.

### Removed
* Chinese translations


## [0.3.1] - 2026-06-05

### Added
* Morbid Mask item.
* Satanic sword item.
* Lifesteal mechanic given by two previous items.

### Changed
* Dire ore generation. Now it spawns more frequently.
* Halve the attack speed bonus given by agility.


## [0.3.2] - 2026-06-07

### Added
* Option to toggle on and off the Defense of the Craft status bar.
* `#defense-of-the-craft:ores` tag which is also included in `#c:ores` tag. This change should make the mod compatible with [AntiXRay](https://modrinth.com/mod/anti-xray) mod.


## [0.3.3] - 2026-06-10

### Changed
* Hand of Midas cooldown is lowered to 2 minutes due to a cooldown resetting bug ([#46](https://github.com/detectivekaktus/defense-of-the-craft/issues/46)). The chances are lowered to:
    * Coal - 45%
    * Iron - 33%
    * Gold - 15%
    * Diamond - 6.6%
    * Netherite - 0.14%
* Hand of Midas no longer works for mobs defined in `#c:bosses` conventional fabric tag. Any mod that does not include their bosses in that tag will see their bosses devoured by the Hand of Midas usage.


## [0.3.4] - 2026-06-11

### Changed
* Sacred Relic is now a sword item.
* Ring of Health health regeneration reduced from 1 to 0.5.
* Ring of Tarrasque health regeneration reduced from 2 to 1.
* Heart of Tarrasque health regeneration and regeneration amplification reduced from 2.5 to 1.5 and from 15% to 6% respectively.
* Sange health regeneration amplification lowered from 12% to 3%.
* Abyssal Blade health regeneration amplification lowered from 12% to 3%.
* Health regeneration gain from strength is reduced from 0.025/s to 0.0125/s.

### Fixed
* Monkey King Bar can now be enchanted.
* Radiance range is now identical to the description.

### Removed
* Ukrainian and Spanish translations.