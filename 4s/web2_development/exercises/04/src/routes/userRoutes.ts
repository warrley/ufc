import { Router } from 'express';
import {
  createUser,
  getUsers,
  getUserById,
  updateUser,
  patchUser,
  deleteUser,
} from '../controllers/userController.js';

const router = Router();

router.post('/', createUser);
router.get('/', getUsers);
router.get('/:id', getUserById);
router.put('/:id', updateUser);
router.patch('/:id', patchUser);
router.delete('/:id', deleteUser);

export default router;
