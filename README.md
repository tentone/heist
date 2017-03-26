# Heist to the museum

## TODO
### Move amIRequired to ControlCollectionSite and avoid MasterThief blocking waiting for "the party that never comes"
Thief 0 reached outside (Position:0)
Thief 1 reached outside (Position:0)
Thief 0 change state 1000
Thief 3 reached outside (Position:0)
Thief 0 handACanvas (HasCanvas:true)
Thief 1 change state 1000
Thief 1 handACanvas (HasCanvas:true)
Master collectCanvas (Total:28)
Thief 3 change state 1000
Thief 3 handACanvas (HasCanvas:false)
Master change state 2000
Master appraiseSit
Thief 1 amINeeded
Thief 0 amINeeded
Thief 0 entered the concentration site
Thief 1 entered the concentration site
Master change state 4000
Master takeARest
Thief 3 amINeeded
Thief 3 entered the concentration site
Master collectCanvas (Total:29)
Master change state 2000
Master appraiseSit
Master change state 4000
Master takeARest

### Avoid sending party's to already completly empty rooms
Master appraiseSit
Master change state 5000
Thief 0 party assigned 24
Thief 0 change state 2000
64 paintings were stolen!!!
Master terminated
Thief 2 amINeeded
Thief 2 terminated
Thief 3 party assigned 23
Thief 3 change state 2000
Thief 1 amINeeded
Thief 1 terminated
Thief 5 party assigned 23
Thief 5 change state 2000
Thief 5 crawlIn (Position:3)
Thief 4 party assigned 23
Thief 4 change state 2000
Thief 4 crawlIn (Position:4)
(...)
Thief 4 crawlIn (Position:26)
Thief 3 crawlIn (Position:13)
Thief 5 reached room (Position:28)
Thief 5 change state 3000
Thief 5 rollACanvas (HasCanvas:false)
Thief 3 crawlIn (Position:14)
(...)
Thief 3 crawlIn (Position:27)
Thief 3 reached room (Position:28)
Thief 3 change state 3000
Thief 3 rollACanvas (HasCanvas:false)
Thief 4 reached room (Position:28)
Thief 4 change state 3000
Thief 4 rollACanvas (HasCanvas:false)
Thief 5 reverse
Thief 5 change state 4000
Thief 5 crawlOut (Position:25)
Thief 3 reverse
Thief 3 change state 4000
Thief 4 reverse
Thief 4 change state 4000
(...)
Thief 5 crawlOut (Position:1)
Thief 4 crawlOut (Position:2)
Thief 3 crawlOut (Position:15)
Thief 5 reached outside (Position:0)
Thief 5 change state 1000
Thief 5 handACanvas (HasCanvas:false)
Thief 3 crawlOut (Position:14)
Thief 3 crawlOut (Position:13)
(...)
Thief 3 crawlOut (Position:1)
Thief 3 reached outside (Position:0)
Thief 3 change state 1000
Thief 3 handACanvas (HasCanvas:false)
Thief 5 amINeeded
Thief 5 terminated
Thief 4 reached outside (Position:0)
Thief 4 change state 1000
Thief 4 handACanvas (HasCanvas:false)
Thief 3 amINeeded
Thief 3 terminated

### Write Logger

### Check random Exception (thievesAttacking < 0)