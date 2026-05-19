# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

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
* Kill streak announcement which can be turned on and off in the mod configuration.


## Fixed
* Radiance and Overwhelming Blink damage now is bound to player, meaning that mob drops will be given as if the player killed the mobs by hand.
* Radiant Crystal Block and Dire Crystal Block can now be reverted back to crystals.


## Changed
* No longer display mana cost for items with mana cost set to 0.

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