# -*- coding: utf8 -*-
from sklearn.feature_extraction.text import TfidfVectorizer
# from konlpy.tag import Okt
# from konlpy.utils import pprint
import pandas as pd
from pandas import Series, DataFrame
import scipy.sparse
import sqlite3
import pymysql.cursors
import pandas as pd
from sqlalchemy import create_engine
# import MySQLdb
from queue import PriorityQueue
import math
# MySQL Connector using pymysql
pymysql.install_as_MySQLdb()




connection = pymysql.connect(host='127.0.0.1', port=3306, user='root', password='adminssafy', db='tripmall_db', charset='utf8', autocommit=True, cursorclass=pymysql.cursors.DictCursor)
cursor = connection.cursor()
sql = "select * from tripmall_db.touranddining"
cursor.execute(sql)
result = cursor.fetchall()
data = pd.DataFrame(result)
# 25 제외, 32
contentidlist=[]
contenttypelist=[]
mapxlist=[]
mapylist=[]



for i, Each_row in data.iterrows():
    contentidlist.append(Each_row['content_id'])
    contenttypelist.append(Each_row['content_type_id'])
    mapxlist.append(Each_row['mapx'])
    mapylist.append(Each_row['mapy'])

length = len(contentidlist)
print(length)
length = len(contenttypelist)
print(length)
length = len(mapxlist)
print(length)
length = len(mapylist)
print(length)
raw_data = {'selected_spot': [], 'near_spot': []}

recommendAuto = DataFrame(raw_data)
engine = create_engine("mysql+mysqldb://root:"+"adminssafy"+"@localhost:3306/tripmall_db", encoding='utf-8')
conn = engine.connect()
for i in range(0, length):
    
    if contenttypelist[i] != 25 and contenttypelist[i] != 32:
        que = PriorityQueue()
        for j in range(0, length):
            if i != j:
                result = math.sqrt(math.pow(mapxlist[i]-mapxlist[j], 2) + math.pow(mapylist[i]-mapylist[j], 2))
                que.put(((result), (contentidlist[i],contentidlist[j])))
        for j in range(0, 10):
            after = que.get()[1]
            

            new_row = {'selected_spot':after[0], 'near_spot':after[1]}
            recommendAuto = recommendAuto.append(new_row, ignore_index=True)
recommendAuto.to_sql(name='distancebased', con=engine, if_exists='append')        




print("finish")