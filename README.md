# Gibberscript
Converting speech into PoS correct gibberish

# What is it
The goal of gibberscript was to support a VR game that simulated what it might be like to hear English as if you were a non-native speaker. Gibberscript provided the sentences NPCs in the game were to speak. These sentences are created by translating English sentences into 'part of speech correct' gibberish.

# PoS Correct 
To maintain similar sentence structures to real English Gibberscript will take in an input sentence and use the Stanford Natural Language Processor to tag it's parts of speech. It will then return a sentence where the words have been replaced with a random english word from the same part of speech (noun -> noun, adjective -> adjective, etc.).
```
The dog howled
```
Becomes
```
A blender read
```

# Building a Cipher
Like any other language English has some words that are used very frequently and I wanted to capture that in Gibberscript. This was done a by creating a cipher, a simple map of words that have already been translated so (following our example from above) if we ever use dog in an input sentence again it would be replaced with blender.

# Dataset
I needed to get words to substitute in from somewhere and it ended up being more difficult than I expected. At first I chose to use novels in the public domain, which worked ok but my sentences would get translated into antiquated words like 'thee' and 'nuncle'. I decided to scrap that dataset and instead grabbed 100 transcribed presidential speeches. These worked much better although idioms and slang are probably underrepresented.

# Expanding
There are a couple cool paths I think this project could take. One could be improving and analysing the data set of words we draw from, rather than grab a word that matches an input words part of speech randomly we could analyse how popular the input word is and swap it with a word of a similar frequency. We could also analayse input words syllables so that a word like dog can't be changed to refrigerator and we preserve a similar sentence cadence. Another route would be instead of changing the words with a preexisting English word we analalyze a dataset and try to reproduce to phonetics of the language. Returning gibberish that is truly unintelligable but still sounds like English.
