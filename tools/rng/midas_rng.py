from datetime import datetime, timedelta
from random import randint


WEIGHTS = {
    "COAL": 800,
    "IRON": 600,
    "GOLD": 600,
    "DIAMOND": 125,
    "NETHERITE": 5
}
WEIGHTS_SUM = sum([val for _, val in WEIGHTS.items()])

PITY_NO_INCREASE = 1
PITY_SOFT_INCREASE = 1.1
PITY_MEDIUM_INCREASE = 1.2
PITY_HIGH_INCREASE = 1.25

PITY_COUNTER_CAP = 16
PITY_TIMESTAMP_CAP = 4 * 60 * 60

PITY_DIAMOND_HARD = 2 << 5

COMEBACK_BOOST_USES = 4

INITIAL_BOOSTED_USES = 15

class Player:
    def __init__(self) -> None:
        self.pity_counter: int = 0
        self.last_roll_timestamp: datetime | None = None
        self.last_login: datetime | None = None
        self.comeback_boost_counter = 0
        self.total_uses = 0

    def login(self) -> None:
        self.last_login = datetime.now()

    def get_comeback_drop_chance(self) -> int:
        self.comeback_boost_counter -= 1
        self.total_uses += 1
        # the +1 is needed to completely skip both coal and copper
        skipped = WEIGHTS["COAL"] + WEIGHTS["IRON"] + 1
        return randint(skipped, WEIGHTS_SUM)

    def get_initial_drop_chance(self) -> int:
        self.total_uses += 1
        skipped = WEIGHTS["COAL"] * 0.875
        return randint(int(skipped), WEIGHTS_SUM)
    
    def get_pity_soft_increase(self) -> float:
        if self.pity_counter < 4:
            return PITY_NO_INCREASE
        elif self.pity_counter >= 4 and self.pity_counter < 8:
            return PITY_SOFT_INCREASE
        elif self.pity_counter >= 10 and self.pity_counter < 13:
            return PITY_MEDIUM_INCREASE
        else:
            return PITY_HIGH_INCREASE

    def get_drop_chance(self) -> int:
        self.last_roll_timestamp = datetime.now()

        if self.total_uses <= INITIAL_BOOSTED_USES:
            return self.get_initial_drop_chance()

        if self.last_login is not None and (datetime.now() - self.last_login).days >= 3:
            self.comeback_boost_counter = COMEBACK_BOOST_USES

        if self.comeback_boost_counter != 0:
            return self.get_comeback_drop_chance()

        if self.pity_counter >= PITY_COUNTER_CAP:
            self.pity_counter = 0
            return PITY_DIAMOND_HARD

        if (datetime.now() - self.last_roll_timestamp).seconds >= PITY_TIMESTAMP_CAP:
            return PITY_DIAMOND_HARD

        self.pity_counter += 1
        self.total_uses += 1
        rand = randint(1, WEIGHTS_SUM)
        increase = self.get_pity_soft_increase()
        return int(rand * increase) if self.pity_counter > 0 else rand

    def use_hand_of_midas(self) -> str:
        rand = self.get_drop_chance()
        if rand == PITY_DIAMOND_HARD:
            self.pity_counter = 0
            return "DIAMOND"

        cumulative = 0
        for item, weight in WEIGHTS.items():
            cumulative += weight
            if rand <= cumulative:
                if item == "DIAMOND" or item == "NETHERITE":
                    self.pity_counter = 0
                return item

        # should be unreachable. it's needed to shut the
        # static analysis fuck up
        return "COAL"


def fill_standard_report(tries: int) -> None:
    print("STANDARD PITY REPORT")

    player = Player()
    player.login()
    player.total_uses = INITIAL_BOOSTED_USES + 1

    results = {
        "COAL": 0,
        "IRON": 0,
        "GOLD": 0,
        "DIAMOND": 0,
        "NETHERITE": 0
    }

    for _ in range(tries):
        results[player.use_hand_of_midas()] += 1

    for item, count in results.items():
        avg = round(count / tries * 100, 3)
        print(f"{item}: {count} (avg {avg}%)")


def fill_initial_report(tries: int) -> None:
    print("INITIAL PITY REPORT")

    player = Player()
    player.login()

    results = {
        "COAL": 0,
        "IRON": 0,
        "GOLD": 0,
        "DIAMOND": 0,
        "NETHERITE": 0
    }

    for _ in range(tries):
        results[player.use_hand_of_midas()] += 1
        player.total_uses = 0

    for item, count in results.items():
        avg = round(count / tries * 100, 3)
        print(f"{item}: {count} (avg {avg}%)")


def fill_welcoming_report(tries: int) -> None:
    print("WELCOMING PITY REPORT")

    player = Player()
    player.login()
    player.last_login = datetime.now() - timedelta(days=3)

    results = {
        "COAL": 0,
        "IRON": 0,
        "GOLD": 0,
        "DIAMOND": 0,
        "NETHERITE": 0
    }

    for _ in range(tries):
        results[player.use_hand_of_midas()] += 1

    for item, count in results.items():
        avg = round(count / tries * 100, 3)
        print(f"{item}: {count} (avg {avg}%)")


def main() -> None:
    tries = 1_000_000

    fill_standard_report(tries)
    print("===================")
    fill_initial_report(tries)
    print("===================")
    fill_welcoming_report(tries)


if __name__ == "__main__":
    main()
