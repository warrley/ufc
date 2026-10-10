import mongoose from "mongoose";

export async function connectDB(): Promise<void> {
  try {
    await mongoose.connect(process.env.MONGO_URI || "mongodb://localhost:27017/crud_express_db");
    console.log("conexao deu certo");
  } catch (error) {
    console.log(error);
  }
}
