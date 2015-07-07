# GitCoins in Clojure

Clojure implementation of a gitcoin miner for the GitCoin cryptocurrency
simulation at Turing.

GitCoin project [here](https://github.com/worace/git-coin).

### Objectives

The hope with this implementation is to take advantage of core.async
to implement a miner program that makes efficient use of a machine's resources.

A few major points include:

* Periodically refresh target from the server without blocking a CPU
* Dynamically scale up miner pool to get maximum use of available CPUs
* Submit successful coins asynchronously without blocking a miner thread

### Design/Todos

* [X] target -- need to store current target as...atom? ref?
* [X] digesting target
* [X] comparing targets -- 1 hash less than another?
* [X] coin verifier -- needs to send messages to server to 
* [ ] miner threads -- agents? independent processes looping on
* [ ] target refreshing -- another indep thread periodically refreshing target

## Usage

TODO: add usage api once it's done

### License

Copyright © 2015 Horace Williams

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
