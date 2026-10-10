import mongoose from 'mongoose';
export async function connectDB() {
    try {
        await mongoose.connect(process.env.MONGO_URI || 'mongodb://localhost:27017/crud_express_db');
        console.log('MongoDB conectado');
    }
    catch (error) {
        console.error('Erro na conexão:', error);
        process.exit(1);
    }
}
