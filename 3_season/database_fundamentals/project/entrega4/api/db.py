import psycopg2

def get_connection():
    return psycopg2.connect(
        dbname="busnol",
        user="postgres",
        password="warley",
        host="localhost" 
    )
