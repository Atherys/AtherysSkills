# AtherysSkills
A Sponge plugin to introduce basic skill-casting abilities for players

## Commands

### Base Commands

| Aliases       | Permission                  | Description                                                     |
|---------------|-----------------------------|-----------------------------------------------------------------|
| `/skill`      | `atherysskills.skill.base`  | Does nothing                                                    |
| `/skill cast` | `atherysskills.skill.cast`  | Casts a given skill. You must have permission to use the skill. |
| `/effect`     | `atherysskills.effect.base` | Does nothing                                                    |

### Admin Commands

| Aliases                               | Permission                    | Description                           |
|---------------------------------------|-------------------------------|---------------------------------------|
| `/effect apply <player> <effect-id>`  | `atherysskills.effect.apply`  | Applies an effect to another player   |
| `/effect remove <player> <effect-id>` | `atherysskills.effect.remove` | Removes an effect from another player |
