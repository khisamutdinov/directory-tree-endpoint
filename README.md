
test input
```
CREATE fruits
CREATE vegetables
CREATE grains
CREATE fruits/apples
CREATE fruits/apples/fuji
LIST
CREATE grains/squash
MOVE grains/squash vegetables
CREATE foods
MOVE grains foods
MOVE fruits foods
MOVE vegetables foods
LIST
DELETE fruits/apples
DELETE foods/fruits/apples
LIST

```
Better (formatted) test input
```
CREATE birds/hummingbird \
CREATE birds/hummingbird/anna \
CREATE animals \
CREATE animals/mammals \
CREATE animals/mammals/penguin \
MOVE animals/mammals/penguin birds \
LIST

```