# -*- coding: utf8 -*-
from sklearn.feature_extraction.text import TfidfVectorizer
from konlpy.tag import Okt
from konlpy.utils import pprint
import pandas as pd
from pandas import Series, DataFrame
import scipy.sparse
import sqlite3
# import pymysql
import pymysql.cursors
import pandas as pd
from sqlalchemy import create_engine
# import MySQLdb
# MySQL Connector using pymysql

pymysql.install_as_MySQLdb()




connection = pymysql.connect(host='127.0.0.1', port=3306, user='root', password='adminssafy', db='tripmall_db', charset='utf8', autocommit=True, cursorclass=pymysql.cursors.DictCursor)
cursor = connection.cursor()
sql = "select * from tripmall_db.foodinfo"
cursor.execute(sql)
result = cursor.fetchall()
data = pd.DataFrame(result)

mydoclist=[]



for i, Each_row in data.iterrows():
    mydoclist.append(Each_row['treat_menus'])

okt = Okt()

doc_nouns_list = []
count=0
for doc in mydoclist:
    # count += 1
    # if count==50000:
    #     break
    nouns = okt.nouns(doc)
    doc_nouns = ''

    for noun in nouns:
        doc_nouns += noun + ' '
    doc_nouns_list.append(doc_nouns)

tfidf_vectorizer = TfidfVectorizer(min_df=1)
tfidf_matrix = tfidf_vectorizer.fit_transform(doc_nouns_list)

document_distances = (tfidf_matrix * tfidf_matrix.T)
# print(document_distances)
# document_distances를 sparse matrix 화 시켜서 이차원 배열에 value가 있는 값으로 전환
first = pd.DataFrame.sparse.from_spmatrix(document_distances)
# raw_data -> dataframe 컬럼을 만들어 놓음
raw_data = {'selected_store': [], 'recommended_store': [], 'similarity' : []}
# raw_data 컬럼을 넣음
recommendAuto = DataFrame(raw_data)
print(first)

# store 전체를 비교할 것이라서 인덱스 0부터 시작하기 때문에 찾을 때에는 1씩 인덱스 올려서 찾으면 됨
for index in range(0, first[0].size):
    # 다 정제가 되었으면 이제 내림차순으로 각각 store를 행별로 정렬 관련된 상위 6개만 일단 뽑아옴(6개 추천해준다는 뜻임)
    second = first[index].sort_values(ascending=False).head(11)
    sql = "select content_id from tripmall_db.foodinfo where fid="+str((index+1))
    cursor.execute(sql)
    result = cursor.fetchall()
    selected = result[0].get('content_id')
    for index2 in range(0, 10):
        # if문은 print 찍어 보면 keys()랑 values 부분 이해가 갈거임
        if second.values[index2] < 0.99 and second.values[index2] > 0:
            sql = "select content_id from tripmall_db.foodinfo where fid="+str((second.keys()[index2]+1))
            cursor.execute(sql)
            result = cursor.fetchall()
            recommended = result[0].get('content_id')
            new_row = {'selected_store':selected, 'recommended_store':recommended, 'similarity':second.values[index2]}
            recommendAuto = recommendAuto.append(new_row, ignore_index=True)

engine = create_engine("mysql+mysqldb://root:"+"adminssafy"+"@localhost:3306/tripmall_db", encoding='utf-8')
conn = engine.connect()
recommendAuto.to_sql(name='menubased', con=engine, if_exists='append')
print("finish")