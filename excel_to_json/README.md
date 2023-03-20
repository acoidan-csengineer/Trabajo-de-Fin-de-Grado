# Program to pass points of interest information from Excel to JSON

- [Description](#description)
- [Usage](#usage)

## Description

Program made with ``python`` to receive information about tourist points of interest from an Excel sheet and transform it into a JSON format adaptable to the entry of points of interest available in the TFG.

## Usage

To use this program, you have to pass it the corresponding Excel file, the sheet of the file where the data are to be found and the output file to which you want to write the data. To do this, several command line arguments must be passed to it, among them:

```python
Help mode: python excel_to_json_pois.py -h

Usage: python excel_to_json_pois.py -i input_file_name.xlsx -s sheet_name [-o output_file_name.json] [-h]

Required arguments:
  -i input_file_name.xlsx, --input input_file_name.xlsx      Excel input file
  -s sheet_name, --sheet sheet_name      Excel sheet containing the data

Optional arguments:
  -h, --help            Show this help message and exit
  -o output_file_name.json, --output output_file_name.json      JSON output file

```
