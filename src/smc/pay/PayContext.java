package smc.pay;

public class PayContext
    extends statemap.FSMContext
{
//---------------------------------------------------------------
// Member methods.
//

    public PayContext(Pay owner)
    {
        this (owner, PayMap.Start);
    }

    public PayContext(Pay owner, PayState initState)
    {
        super (initState);

        _owner = owner;
    }

    @Override
    public void enterStartState()
    {
        getState().entry(this);
        return;
    }

    public void CardNotValid()
    {
        _transition = "CardNotValid";
        getState().CardNotValid(this);
        _transition = "";
        return;
    }

    public void CheckCardBalance()
    {
        _transition = "CheckCardBalance";
        getState().CheckCardBalance(this);
        _transition = "";
        return;
    }

    public void DoneTransaction()
    {
        _transition = "DoneTransaction";
        getState().DoneTransaction(this);
        _transition = "";
        return;
    }

    public void IsValidCard()
    {
        _transition = "IsValidCard";
        getState().IsValidCard(this);
        _transition = "";
        return;
    }

    public void LowBalance()
    {
        _transition = "LowBalance";
        getState().LowBalance(this);
        _transition = "";
        return;
    }

    public void ReadyForPay()
    {
        _transition = "ReadyForPay";
        getState().ReadyForPay(this);
        _transition = "";
        return;
    }

    public void SendToMerchant()
    {
        _transition = "SendToMerchant";
        getState().SendToMerchant(this);
        _transition = "";
        return;
    }

    public void Stopped()
    {
        _transition = "Stopped";
        getState().Stopped(this);
        _transition = "";
        return;
    }

    public PayState getState()
        throws statemap.StateUndefinedException
    {
        if (_state == null)
        {
            throw(
                new statemap.StateUndefinedException());
        }

        return ((PayState) _state);
    }

    protected Pay getOwner()
    {
        return (_owner);
    }

    public void setOwner(Pay owner)
    {
        if (owner == null)
        {
            throw (
                new NullPointerException(
                    "null owner"));
        }
        else
        {
            _owner = owner;
        }

        return;
    }

//---------------------------------------------------------------
// Member data.
//

    transient private Pay _owner;

    //-----------------------------------------------------------
    // Constants.
    //

    private static final long serialVersionUID = 1L;

//---------------------------------------------------------------
// Inner classes.
//

    public static abstract class PayState
        extends statemap.State
    {
    //-----------------------------------------------------------
    // Member methods.
    //

        protected PayState(String name, int id)
        {
            super (name, id);
        }

        protected void entry(PayContext context) {}
        protected void exit(PayContext context) {}

        protected void CardNotValid(PayContext context)
        {
            Default(context);
        }

        protected void CheckCardBalance(PayContext context)
        {
            Default(context);
        }

        protected void DoneTransaction(PayContext context)
        {
            Default(context);
        }

        protected void IsValidCard(PayContext context)
        {
            Default(context);
        }

        protected void LowBalance(PayContext context)
        {
            Default(context);
        }

        protected void ReadyForPay(PayContext context)
        {
            Default(context);
        }

        protected void SendToMerchant(PayContext context)
        {
            Default(context);
        }

        protected void Stopped(PayContext context)
        {
            Default(context);
        }

        protected void Default(PayContext context)
        {
            throw (
                new statemap.TransitionUndefinedException(
                    "State: " +
                    context.getState().getName() +
                    ", Transition: " +
                    context.getTransition()));
        }

    //-----------------------------------------------------------
    // Member data.
    //
    }

    /* package */ static abstract class PayMap
    {
    //-----------------------------------------------------------
    // Member methods.
    //

    //-----------------------------------------------------------
    // Member data.
    //

        //-------------------------------------------------------
        // Constants.
        //

        public static final PayMap_Start Start =
            new PayMap_Start("PayMap.Start", 0);
        public static final PayMap_CheckCard CheckCard =
            new PayMap_CheckCard("PayMap.CheckCard", 1);
        public static final PayMap_CheckBalance CheckBalance =
            new PayMap_CheckBalance("PayMap.CheckBalance", 2);
        public static final PayMap_UpdatedBalance UpdatedBalance =
            new PayMap_UpdatedBalance("PayMap.UpdatedBalance", 3);
        public static final PayMap_PaymentSettled PaymentSettled =
            new PayMap_PaymentSettled("PayMap.PaymentSettled", 4);
        public static final PayMap_InvalidCard InvalidCard =
            new PayMap_InvalidCard("PayMap.InvalidCard", 5);
        public static final PayMap_NoBalance NoBalance =
            new PayMap_NoBalance("PayMap.NoBalance", 6);
        public static final PayMap_Stop Stop =
            new PayMap_Stop("PayMap.Stop", 7);
        public static final PayMap_End End =
            new PayMap_End("PayMap.End", 8);
    }

    protected static class PayMap_Default
        extends PayState
    {
    //-----------------------------------------------------------
    // Member methods.
    //

        protected PayMap_Default(String name, int id)
        {
            super (name, id);
        }

    //-----------------------------------------------------------
    // Member data.
    //

        //---------------------------------------------------
        // Constants.
        //

        private static final long serialVersionUID = 1L;
    }

    private static final class PayMap_Start
        extends PayMap_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private PayMap_Start(String name, int id)
        {
            super (name, id);
        }

        @Override
        protected void ReadyForPay(PayContext context)
        {
            Pay ctxt = context.getOwner();

            (context.getState()).exit(context);
            context.clearState();
            try
            {
                ctxt.initiated();
            }
            finally
            {
                context.setState(PayMap.CheckCard);
                (context.getState()).entry(context);
            }

            return;
        }

    //-------------------------------------------------------
    // Member data.
    //

        //---------------------------------------------------
        // Constants.
        //

        private static final long serialVersionUID = 1L;
    }

    private static final class PayMap_CheckCard
        extends PayMap_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private PayMap_CheckCard(String name, int id)
        {
            super (name, id);
        }

        @Override
        protected void IsValidCard(PayContext context)
        {
            Pay ctxt = context.getOwner();

            if (ctxt.isValidCard())
            {
                (context.getState()).exit(context);
                context.clearState();
                try
                {
                    ctxt.cardAuthSuccess();
                }
                finally
                {
                    context.setState(PayMap.CheckBalance);
                    (context.getState()).entry(context);
                }

            }
            else
            {
                (context.getState()).exit(context);
                context.clearState();
                try
                {
                    ctxt.cardAuthFailed();
                }
                finally
                {
                    context.setState(PayMap.InvalidCard);
                    (context.getState()).entry(context);
                }

            }

            return;
        }

    //-------------------------------------------------------
    // Member data.
    //

        //---------------------------------------------------
        // Constants.
        //

        private static final long serialVersionUID = 1L;
    }

    private static final class PayMap_CheckBalance
        extends PayMap_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private PayMap_CheckBalance(String name, int id)
        {
            super (name, id);
        }

        @Override
        protected void CheckCardBalance(PayContext context)
        {
            Pay ctxt = context.getOwner();

            if (ctxt.checkBalance())
            {
                (context.getState()).exit(context);
                context.clearState();
                try
                {
                    ctxt.updateBalance();
                }
                finally
                {
                    context.setState(PayMap.UpdatedBalance);
                    (context.getState()).entry(context);
                }

            }
            else
            {
                (context.getState()).exit(context);
                context.clearState();
                try
                {
                    ctxt.noBalance();
                }
                finally
                {
                    context.setState(PayMap.NoBalance);
                    (context.getState()).entry(context);
                }

            }

            return;
        }

    //-------------------------------------------------------
    // Member data.
    //

        //---------------------------------------------------
        // Constants.
        //

        private static final long serialVersionUID = 1L;
    }

    private static final class PayMap_UpdatedBalance
        extends PayMap_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private PayMap_UpdatedBalance(String name, int id)
        {
            super (name, id);
        }

        @Override
        protected void SendToMerchant(PayContext context)
        {
            Pay ctxt = context.getOwner();

            (context.getState()).exit(context);
            context.clearState();
            try
            {
                ctxt.sendToMerchant();
            }
            finally
            {
                context.setState(PayMap.PaymentSettled);
                (context.getState()).entry(context);
            }

            return;
        }

    //-------------------------------------------------------
    // Member data.
    //

        //---------------------------------------------------
        // Constants.
        //

        private static final long serialVersionUID = 1L;
    }

    private static final class PayMap_PaymentSettled
        extends PayMap_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private PayMap_PaymentSettled(String name, int id)
        {
            super (name, id);
        }

        @Override
        protected void DoneTransaction(PayContext context)
        {

            (context.getState()).exit(context);
            context.setState(PayMap.Stop);
            (context.getState()).entry(context);
            return;
        }

    //-------------------------------------------------------
    // Member data.
    //

        //---------------------------------------------------
        // Constants.
        //

        private static final long serialVersionUID = 1L;
    }

    private static final class PayMap_InvalidCard
        extends PayMap_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private PayMap_InvalidCard(String name, int id)
        {
            super (name, id);
        }

        @Override
        protected void CardNotValid(PayContext context)
        {
            Pay ctxt = context.getOwner();

            (context.getState()).exit(context);
            context.clearState();
            try
            {
                ctxt.invalidCard();
            }
            finally
            {
                context.setState(PayMap.Stop);
                (context.getState()).entry(context);
            }

            return;
        }

    //-------------------------------------------------------
    // Member data.
    //

        //---------------------------------------------------
        // Constants.
        //

        private static final long serialVersionUID = 1L;
    }

    private static final class PayMap_NoBalance
        extends PayMap_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private PayMap_NoBalance(String name, int id)
        {
            super (name, id);
        }

        @Override
        protected void LowBalance(PayContext context)
        {

            (context.getState()).exit(context);
            context.setState(PayMap.Stop);
            (context.getState()).entry(context);
            return;
        }

    //-------------------------------------------------------
    // Member data.
    //

        //---------------------------------------------------
        // Constants.
        //

        private static final long serialVersionUID = 1L;
    }

    private static final class PayMap_Stop
        extends PayMap_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private PayMap_Stop(String name, int id)
        {
            super (name, id);
        }

        @Override
        protected void Stopped(PayContext context)
        {
            Pay ctxt = context.getOwner();

            (context.getState()).exit(context);
            context.clearState();
            try
            {
                ctxt.stopped();
            }
            finally
            {
                context.setState(PayMap.End);
                (context.getState()).entry(context);
            }

            return;
        }

    //-------------------------------------------------------
    // Member data.
    //

        //---------------------------------------------------
        // Constants.
        //

        private static final long serialVersionUID = 1L;
    }

    private static final class PayMap_End
        extends PayMap_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private PayMap_End(String name, int id)
        {
            super (name, id);
        }

    //-------------------------------------------------------
    // Member data.
    //

        //---------------------------------------------------
        // Constants.
        //

        private static final long serialVersionUID = 1L;
    }
}

/*
 * Local variables:
 *  buffer-read-only: t
 * End:
 */
