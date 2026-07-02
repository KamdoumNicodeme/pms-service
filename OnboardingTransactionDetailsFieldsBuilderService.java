Salut [prénom],

Petit point métier sur le dev ROP concernant l'accountCode.

Aujourd'hui l'accountCode est récupéré via le business referrer lié au broker de la policy. Mais ça ne marche que si le broker est aussi business referrer, ce qui est rare. Dans tous les autres cas, on n'a pas d'accountCode (null).

Comment veux-tu qu'on gère ce cas ?
- soit on accepte le null et on désactive la validation côté Salesforce pour que l'opportunité se crée quand même,
- soit tu me donnes une valeur d'accountCode par défaut à mapper.

En gros : est-ce qu'un ROP peut exister sans accountCode, ou il en faut toujours un ?

Merci !
