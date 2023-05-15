import pandas as pd

def main():
    csv = pd.read_csv('assets/jeopardy-questions/JEOPARDY_CSV.csv')

    c = 0

    for l in csv.iterrows():
        if c == 0:
            c += 1
            continue
        
        category = l[1]['Category']
        value: str = l[1]['Value'].replace(',', '')
        question = l[1]['Question']
        answer = l[1]['Answer']

        file_name = value.replace('$', '').replace(',', '')

        with open('assets/jeopardy-questions/' + file_name + '.csv', 'a') as f:
            f.write(f'"{category}","{value}","{question}","{answer}"\n')

 

if __name__ == '__main__':
    main()