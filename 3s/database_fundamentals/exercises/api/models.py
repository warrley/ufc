
from pydantic import BaseModel
from datetime import date
from typing import Optional

class Departamento(BaseModel):
    dnumero: int
    dnome: str
    cpf_gerente: Optional[str] = None
    data_inicio_gerente: Optional[date] = None
    
class DepartamentoUpdate(BaseModel):
    dnome: str
    cpf_gerente: Optional[str] = None
    data_inicio_gerente: Optional[date] = None
     
