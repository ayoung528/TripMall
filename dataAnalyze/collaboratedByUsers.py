# -*- coding: utf8 -*-
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
# from konlpy.tag import Okt
# from konlpy.utils import pprint
import pandas as pd
from pandas import Series, DataFrame
import scipy.sparse
import sqlite3
# import pymysql
import pymysql.cursors
import pandas as pd
from sqlalchemy import create_engine
import platform
# import MySQLdb
# MySQL Connector using pymysql

pymysql.install_as_MySQLdb()

def get_user_based_collabor(contentid):
    return user_based_collabor[contentid].sort_values(ascending=False)[:6]

platform = platform.system()
hostAdd = ""
if platform == "Windows":
    hostAdd = '127.0.0.1';
else :
    hostAdd = 'k3b302.p.ssafy.io'
connection = pymysql.connect(host=hostAdd, port=3306, user='root', password='adminssafy', db='tripmall_db', charset='utf8', autocommit=True, cursorclass=pymysql.cursors.DictCursor)
cursor = connection.cursor()
sql = "delete from tripmall_db.userbased"
cursor.execute(sql)
cursor = connection.cursor()
sql = "select * from tripmall_db.touranddining"
cursor.execute(sql)
result = cursor.fetchall()
content_data = pd.DataFrame(result)
sql = "select * from tripmall_db.review"
cursor.execute(sql)
result = cursor.fetchall()
review_data = pd.DataFrame(result)
sql = "select * from tripmall_db.surveyresult"
cursor.execute(sql)
result = cursor.fetchall()
survey_data = pd.DataFrame(result)

mydoclist=[]

survey_data.drop('sid', axis=1, inplace=True)

review_data.drop('rid', axis=1, inplace=True)
review_data.drop('comment', axis=1, inplace=True)
review_data.drop('write_time', axis=1, inplace=True)

content_data.drop('addr1', axis=1, inplace=True)
content_data.drop('addr2', axis=1, inplace=True)
content_data.drop('areacode', axis=1, inplace=True)
content_data.drop('cat1', axis=1, inplace=True)
content_data.drop('cat2', axis=1, inplace=True)
content_data.drop('cat3', axis=1, inplace=True)
content_data.drop('content_type_id', axis=1, inplace=True)
content_data.drop('first_image', axis=1, inplace=True)
content_data.drop('first_image2', axis=1, inplace=True)
content_data.drop('mapx', axis=1, inplace=True)
content_data.drop('mapy', axis=1, inplace=True)
content_data.drop('sigungucode', axis=1, inplace=True)
content_data.drop('tel', axis=1, inplace=True)
content_data.drop('title', axis=1, inplace=True)
content_data.drop('readcount', axis=1, inplace=True)
content_data.drop('creation_date', axis=1, inplace=True)
content_data.drop('modified_date', axis=1, inplace=True)
content_data.drop('score', axis=1, inplace=True)

review_content_rating = pd.merge(review_data, content_data, on='content_id')
review_content_rating = pd.concat([review_content_rating, survey_data])
# print(review_content_rating.head(10))
content_user_rating = review_content_rating.pivot_table('score', index='content_id', columns='uid')
content_user_rating.fillna(0, inplace=True)

user_based_collabor = cosine_similarity(content_user_rating)

user_based_collabor = pd.DataFrame(data = user_based_collabor, index=content_user_rating.index, columns=content_user_rating.index)
# print(user_based_collabor.head(10))

contents = user_based_collabor.index
raw_data = {'selected_content': [], 'recommended_content': [], 'similarity' : []}

recommendAuto = DataFrame(raw_data)

for content in contents:
    top6 = get_user_based_collabor(content)
    # print(content in top6.index)
    if content in top6.index :
        top5 = top6.drop([content])
    else : 
        top5 = top6

    for row in top5.iteritems():
        new_row = {'selected_content':content, 'recommended_content':row[0], 'similarity':row[1]}
        recommendAuto = recommendAuto.append(new_row, ignore_index=True)
    # print("----------------------")

engine = create_engine("mysql+mysqldb://root:"+"adminssafy"+"@localhost:3306/tripmall_db", encoding='utf-8')
conn = engine.connect()
recommendAuto.to_sql(name='userbased', con=engine, if_exists='append')
print(recommendAuto)

print("finish")