#!/usr/bin/env python3.5

import sys
from flask import Flask, Response
import json
from pyspark import SparkContext, SparkConf, HiveContext, SparkFiles
from pyspark.sql import SQLContext, SparkSession

sc = None

def init():
		global sc
		conf = SparkConf().setAppName("ORC READ")
		conf.set('spark.ui.enabled', False)
		conf.set('spark.driver.memory', '60g')
		conf.set('spark.executor.memory ', '60g')
		sc = SparkSession.builder.config(conf=conf).config("hive.metastore.uris", "thrift://10.7.151.3:9083").enableHiveSupport().getOrCreate()
		sc.sparkContext.setLogLevel("ERROR")
		
init()

app = Flask(__name__)

@app.route('/hive/<string>', methods=['GET'])
def hive(string):
	try:
		global sc
		hive_context = HiveContext(sc)
		colunas, nome_tab, condicao, compl = string.split("-")
		
		if condicao != '0':			
			condicao = condicao.replace('_', ' ')
			condicao = 'and ' + condicao
		else:
			condicao = ''
		
		if compl != '0':
			compl = compl.replace('_', ' ')		
			return str(hive_context.sql("select " + colunas + " from " + nome_tab + " where 1=1 " + condicao + compl).collect())
		else:
			return str(hive_context.sql("select " + colunas + " from " + nome_tab + " where 1=1 " + condicao).collect())
		
	except e:
		pass

app.run(host='0.0.0.0', port=50111, threaded=True, debug=False)


 
