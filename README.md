#AutoLogistics

A mod about automation scripts.

##Scripts Syntax

The following script automatically processes wood in furnaces:

```
charcoal = <minecraft:charcoal>
chest = <minecraft:chest>
wood = <minecraft:oak_log>
furnace = <minecraft:furnace>

while(true){
  chest@[wood] >> furnace@[top]
  chest@[charcoal] >> furnace@[north]
  furnace@[bottom] >> chest
}
```
