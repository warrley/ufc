from fastapi import FastAPI
from crud_rodoviaria import router as rodoviaria_router

app = FastAPI(
    title="API Rodoviárias",
    version="1.0"
)

app.include_router(rodoviaria_router, prefix="/rodoviarias", tags=["Rodoviárias"])