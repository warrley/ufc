from fastapi import FastAPI
from crud_departamento import router as departamento_router

app = FastAPI()

@app.get("/")
def olamundo():
    return {"msg": "ok"}

app.include_router(departamento_router, prefix="/departamentos")
