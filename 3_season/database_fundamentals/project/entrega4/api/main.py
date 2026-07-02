from fastapi import FastAPI
from crud_rodoviaria import router as rodoviaria_router

app = FastAPI()

@app.get("/")
def teste():
    return {"msg": "ok"}

app.include_router(rodoviaria_router, prefix="/rodoviarias")
