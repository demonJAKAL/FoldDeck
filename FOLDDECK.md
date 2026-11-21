# FoldDeck

FoldDeck is a personal build of [DroidDeck](https://github.com/Droid-Deck/DroidDeck) tuned for the Samsung Galaxy Z Fold 7.
All of the Steam, Proton, Linux runtime and store work is DroidDeck's. FoldDeck only changes the items below.
FoldDeck is not affiliated with the DroidDeck team, Valve or Samsung.

The DroidDeck version FoldDeck is based on is recorded in [UPSTREAM.txt](UPSTREAM.txt).

## What FoldDeck changes

- Installs as its own app (`com.folddeck.app`, name "FoldDeck", version 1.0.0), so it sits beside DroidDeck instead of replacing it.
- Flex mode: when the phone is half folded with the hinge across the screen, the game is shown above the hinge and the on-screen controls below it. Turn it off with the `flexMode` setting (on by default).
- Upstream-only CI steps (authorship check, signing, publishing) run only in the official DroidDeck repository.

DroidDeck's own fold support (controls below the game on the unfolded screen, hiding them when a controller connects, and resizing when you fold or unfold) is kept unchanged.

## Licence and credits

FoldDeck is licensed under the GNU General Public License v3.0, the same as DroidDeck. See [LICENSE](LICENSE).
Full credits for DroidDeck and the projects it builds on are in [README.md](README.md#credits-and-licence).
Steam and Proton belong to Valve Corporation.

Source for every FoldDeck build is this repository.
