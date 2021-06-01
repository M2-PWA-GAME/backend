# backend

## MAP JSON 

```json
{
  "game" : {
    "turn1": {
        "actions" : [],
        "states" : [
            { "name": "KunfuPanda" ,"x": 1, "y": 8 , "health" : 20 },
            {"name": "Alban", "x": 5, "y": 17 , "health" : 18 },
        ],
        "objects" : [{"TYPE":"armor", "x":3, "y":9 } ]
    },
    "turn2": {
        "player": "Alban",
        "actions" : [
          {"type":"deplacement","from": {"x":5,"y":17}, "to": {"x":8,"y":18}},
          {"2": "taper","degat": 4, "target": "KunfuPanda"}
        ],
        "states" : [
            { "name": "KunfuPanda" ,"x": 1, "y": 8 , "health" : 16 },
            { "name": "Alban", "x": 8, "y": 18 , "health" : 18 },
        ],
        "objects" : [{"TYPE":"armor", "x":3, "y":9 }]
    }
  }
}
```