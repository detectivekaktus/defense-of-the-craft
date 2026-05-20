from argparse import ArgumentParser
from json import load
from pprint import pp


def setup_parser() -> ArgumentParser:
    parser = ArgumentParser()
    parser.add_argument(
        "-s",
        "--source",
        required=True,
        help="JSON file with all the translations already implemented",
        type=str
    )
    parser.add_argument(
        "file",
        help="File to compare against the source",
        type=str
    )

    return parser


def main() -> None:
    parser = setup_parser()
    args = parser.parse_args()

    with open(args.source, "r", encoding="utf-8") as f:
        translations: dict[str, str] = load(f)
    
    with open(args.file, "r", encoding="utf-8") as f:
        compared: dict[str, str] = load(f)

    to_be_translated: dict[str, str] = {}
    for key in translations.keys():
        if key not in compared:
            to_be_translated[key] = translations[key]

    pp(to_be_translated, width=255)


if __name__ == "__main__":
    main()