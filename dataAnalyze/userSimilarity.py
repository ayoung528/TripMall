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
sql = "delete from tripmall_db.usersimilaritybased"
cursor.execute(sql)
cursor = connection.cursor()
sql = "select * from tripmall_db.user"
cursor.execute(sql)
result = cursor.fetchall()
data = pd.DataFrame(result)
print(data)

mydoclist=[]
mydoclist2=[]



for i, Each_row in data.iterrows():
    mydoclist.append(Each_row['gender'])
    if Each_row['age_range']=="0~9":
        mydoclist2.append("영유아")
    elif Each_row['age_range']=="10~19":
        mydoclist2.append("10대")
    elif Each_row['age_range']=="20~29":
        mydoclist2.append("20대")
    elif Each_row['age_range']=="30~39":
        mydoclist2.append("30대")
    elif Each_row['age_range']=="40~49":
        mydoclist2.append("40대")
    elif Each_row['age_range']=="50~59":
        mydoclist2.append("50대")
    elif Each_row['age_range']=="60~69":
        mydoclist2.append("60대")
    elif Each_row['age_range']=="70~79":
        mydoclist2.append("70대")
    elif Each_row['age_range']=="80~89":
        mydoclist2.append("80대")
    elif Each_row['age_range']=="90~":
        mydoclist2.append("90대")
    else:
        mydoclist2.append(Each_row['age_range'])


okt = Okt()
length = len(mydoclist)
doc_nouns_list = []
doc_nouns_list2 = []
# print(mydoclist)
# print(mydoclist2)
for doc in mydoclist2:
    doc_nouns2 = ''

    if doc=="none":
        doc = "영유아 10대 20대 30대 40대 50대 60대 70대 80대 90대"
    doc_nouns2 += doc + ' '
    doc_nouns_list2.append(doc_nouns2)
# print(doc_nouns_list2)    
for doc in mydoclist:
    doc_nouns = ''

    if doc=="none":
        doc = "male female"
    doc_nouns += doc + ' '
    doc_nouns_list.append(doc_nouns)
    
for i in range(0,length):
    doc_nouns_list[i] = doc_nouns_list[i] + doc_nouns_list2[i]
# print(doc_nouns_list)
tfidf_vectorizer = TfidfVectorizer(min_df=1)
tfidf_matrix = tfidf_vectorizer.fit_transform(doc_nouns_list)

document_distances = (tfidf_matrix * tfidf_matrix.T)
# print(document_distances)
# document_distances를 sparse matrix 화 시켜서 이차원 배열에 value가 있는 값으로 전환
first = pd.DataFrame.sparse.from_spmatrix(document_distances)
# raw_data -> dataframe 컬럼을 만들어 놓음
raw_data = {'selected_user': [], 'recommended_user': [], 'similarity' : []}
# raw_data 컬럼을 넣음
recommendAuto = DataFrame(raw_data)
# print(first)

# store 전체를 비교할 것이라서 인덱스 0부터 시작하기 때문에 찾을 때에는 1씩 인덱스 올려서 찾으면 됨
for index in range(0, first[0].size):
    # 다 정제가 되었으면 이제 내림차순으로 각각 store를 행별로 정렬 관련된 상위 6개만 일단 뽑아옴(6개 추천해준다는 뜻임)
    second = first[index].sort_values(ascending=False).head(6)
    sql = "select email from tripmall_db.user where id="+str((index+1))
    cursor.execute(sql)
    result = cursor.fetchall()
    selected = result[0].get('email')
    
    for index2 in range(0, first[0].size):
        # if문은 print 찍어 보면 keys()랑 values 부분 이해가 갈거임
        
        sql = "select email from tripmall_db.user where id="+str((second.keys()[index2]+1))
        cursor.execute(sql)
        result = cursor.fetchall()
        recommended = result[0].get('email')
        if selected != recommended:
            new_row = {'selected_user':selected, 'recommended_user':recommended, 'similarity':second.values[index2]}
            recommendAuto = recommendAuto.append(new_row, ignore_index=True)
# print(recommendAuto)
engine = create_engine("mysql+mysqldb://root:"+"adminssafy"+"@localhost:3306/tripmall_db", encoding='utf-8')
conn = engine.connect()
recommendAuto.to_sql(name='usersimilaritybased', con=engine, if_exists='append')
print("finish")