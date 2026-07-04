from pydantic import BaseModel
from typing import Optional

class Rodoviaria(BaseModel):
    id_rodoviaria: Optional[int] = None
    nome: str
    cep: str
    rua: str
    numero: str
    bairro: str
    id_cidade: int

class RodoviariaUpdate(BaseModel):
    nome: Optional[str] = None
    cep: Optional[str] = None
    rua: Optional[str] = None
    numero: Optional[str] = None
    bairro: Optional[str] = None
    id_cidade: Optional[int] = None
