# MyWeather

Implemented as much as possible with the time I could manage.
Implemented the Data, domain and UI layer following the Uniidirectional State flow approach which I found first here:
https://fragmentedpodcast.com/tag/unidirectional-state-flow/

Added DII with Hilt
Next step would be implement WorkManager to do the periodic sync which will be in a manager class and after sync it will push to room DB. Observers to the Room db live data will get the event notification and update he UI
Also unit test would be next.
