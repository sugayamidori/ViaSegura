"use client";

import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@viasegura/components/ui/card";
import { Activity, MapPin } from "lucide-react";
import { RegisterForm } from "@viasegura/modules/auth/components/register-form";

const Register = () => {
  return (
    <div className="min-h-screen bg-gradient-hero flex flex-col items-center justify-center p-6">
      <div className="flex items-center justify-center gap-3 mb-8">
        <div className="relative">
          <MapPin className="h-10 w-10 text-primary" />
          <Activity className="h-5 w-5 text-accent absolute -bottom-1 -right-1" />
        </div>
        <span className="text-2xl font-bold bg-gradient-primary bg-clip-text text-transparent">
          ViaSegura
        </span>
      </div>
      <Card className="border-border/50 shadow-elegant w-full max-w-md">
        <CardHeader className="text-center">
          <CardTitle className="text-2xl">Criar conta</CardTitle>
          <CardDescription>
            Comece sua análise de dados gratuitamente
          </CardDescription>
        </CardHeader>
        <CardContent className="space-y-6">
          <RegisterForm />
        </CardContent>
      </Card>
    </div>
  );
};

export default Register;
