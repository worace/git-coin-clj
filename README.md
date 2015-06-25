# GitCoins in Clojure

Clojure implementation of a gitcoin miner for the GitCoin cryptocurrency
simulation at Turing.

GitCoin project [here](https://github.com/worace/git-coin).

### Design/Todos

miner components

* [ ] target -- need to store current target as...atom? ref?
* [ ] miner threads -- agents? independent processes looping on
* [ ] digesting target
* [ ] comparing targets -- 1 hash less than another?
* [ ] target refreshing -- another indep thread periodically refreshing target
* [ ] coin verifier -- needs to send messages to server to 


## Usage

TODO: add usage api once it's done

### License

Copyright © 2015 Horace Williams

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
