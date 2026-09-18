case 'name':
    console.log('[MORAL NAME] BEFORE =', client.name);
    console.log('[MORAL NAME] VALUE =', value);

    client.name = value ?? '';

    console.log('[MORAL NAME] AFTER =', client.name);
    break;
