# backend

## MAP JSON 

```json
{
  "game": {
    "name": "Alban vs Kunfu",
    "playersId": ["$objectID(Alban)", "$objectID(KunfuPanda)"],
    "rounds": [
      {
        "id": 1,
        "player": null,
        "finish": true,
        "turns": [
          {
            "id": 0,
            "actions": [],
            "player": null,
            "players_states": [
              {
                "id": "$objectID(KunfuPanda)",
                "position": { "x": 1, "y": 8 },
                "health": 20,
                "weapon": { "name": "knife", "damage": 1, "range": 1 },
                "armor": { "name": "shoes", "health": 1 }
              },
              {
                "id": "$objectID(Alban)",
                "position": { "x": 5, "y": 17 },
                "health": 18,
                "weapon": { "name": "sword", "damage": 4, "range": 2 },
                "armor": { "name": "shield", "health": 4 }
              }
            ],
            "objects": [
              {
                "type": "armor",
                "object": { "name": "helmet", "health": 4 },
                "position": { "x": 3, "y": 9 }
              }
            ]
          },
          {
            "id": 1,
            "player": "$objectID(Alban)",
            "actions": [
              {
                "type": "deplacement",
                "from": { "x": 5, "y": 17 },
                "to": { "x": 8, "y": 18 }
              },
              {
                "type": "taper",
                "from": { "x": 8, "y": 18 },
                "to": { "x": 1, "y": 8 }
              }
            ],
            "players_states": [
              {
                "name": "$objectID(KunfuPanda)",
                "position": { "x": 1, "y": 8 },
                "health": 17,
                "weapon": { "name": "knife", "damage": 1, "range": 2 },
                "armor": null
              },
              {
                "name": "$objectID(Alban)",
                "position": { "x": 8, "y": 18 },
                "health": 18,
                "weapon": { "name": "sword", "damage": 4, "range": 2 },
                "armor": { "name": "shield", "health": 4 }
              }
            ],
            "objects": [{ "TYPE": "armor", "x": 3, "y": 9 }]
          }
        ]
      }
    ]
  }
}
```