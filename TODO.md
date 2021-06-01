

## /user ... => jwt 

## GET /user/games => recuperer toutes les parites d"un utilisateur


## GET /games/active/${GameId}  => savoir si une partie est rejoignable
res
```json
{
  "active" : true | false,
  "msg" : "partie en cours"
}
```

## POST /games => creer une partie
UserId => token
req
```json
{
  "name": "Alban vs Kunfu",
  "player_count": 3
}
```

res
```json
{
  "id": "Alban vs Kunfu",
  "join_url": "https://....../games/join/${gameid}"
}
```

## POST /games/join/${GameId} => rejoindre une partie
UserId => token
req
```json
{
  
  
}
```
res
```json
{
  "joinned": true | false,
  "msg" : "Que le meilleure gagne"
}
```

## GET /games/${GameId} => renvoie les coups d'une partie q'un joueur n' pas encore vue
UserId => token
res
```json
cf README.md
```